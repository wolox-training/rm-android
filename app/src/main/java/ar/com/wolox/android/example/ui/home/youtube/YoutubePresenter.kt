package ar.com.wolox.android.example.ui.home.youtube

import ar.com.wolox.android.example.model.youtube.Item
import ar.com.wolox.android.example.network.YoutubeService
import ar.com.wolox.android.example.utils.networkCallback
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class YoutubePresenter @Inject constructor(private val youtubeService: YoutubeService) : BasePresenter<IYoutubeView>() {

    private var listVideos = mutableListOf<Item>()
    private var nexPageT: String = ""

    fun onSearchButtonClicked(searchText: String) {
        restartValues()
        if (searchText.isNotEmpty()) {
            loadVideoList(searchText)
        } else {
            view.showEmptySearchError()
            view.showPlaceholder()
        }
    }

    fun onSelectedItem(itemPosition: Int) {
        view.onItemVideoClicked(listVideos[itemPosition].id.videoId)
    }

    private fun loadVideoList(searchText: String) {
        view.hideKeyBoard()
        view.showProgressBar()
        youtubeService.searchVideo(searchText).enqueue(
                networkCallback {
                    onResponseSuccessful {
                        it?.apply {
                            nexPageT = nextPageToken
                            listVideos.addAll(items)
                        }
                        view.hideProgressBar()
                        view.hidePlaceholder()
                        view.onYoutubeSearchReceived(listVideos)
                    }
                    onResponseFailed { _, _ ->
                        view.hideProgressBar()
                        view.showPlaceholder()
                        view.showError()
                    }
                    onCallFailure {
                        view.hideProgressBar()
                        view.showPlaceholder()
                        view.showError()
                    }
                }
        )
    }

    fun loadMoreVideos() {
        if (nexPageT.isNotEmpty()) {
            view.showProgressBar()
            youtubeService.searchMoreVideos(nexPageT).enqueue(
                    networkCallback {
                        onResponseSuccessful {
                            it?.apply {
                                nexPageT = nextPageToken
                                items.forEach {
                                    i -> listVideos.add(i)
                                }
                            }
                            view.onYoutubeSearchReceived(listVideos)
                            view.hideProgressBar()
                        }
                        onResponseFailed { _, _ ->
                            view.hideProgressBar()
                            view.showError()
                        }
                        onCallFailure {
                            view.hideProgressBar()
                            view.showError()
                        }
                    })
        }
    }

    private fun restartValues() {
        nexPageT = ""
        listVideos.clear()
        view.onYoutubeSearchReceived(listVideos)
    }
}