package ar.com.wolox.android.example.ui.home.news

import android.app.Application
import ar.com.wolox.android.example.model.New
import ar.com.wolox.android.example.network.NewsService
import ar.com.wolox.android.example.utils.NetworkUtils
import ar.com.wolox.android.example.utils.UserSession
import ar.com.wolox.android.example.utils.networkCallback
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices
import javax.inject.Inject

class NewsPresenter @Inject constructor(private val mRetrofitServices: RetrofitServices, private val mUserSession: UserSession, private val mApplication: Application) : BasePresenter<INewsView>() {

    private var listNews = mutableListOf<New>()

    override fun onViewAttached() {
        runIfViewAttached { _ -> getNews() }
    }

    fun refreshNews() {
        restoreOffset()
        getNews()
    }

    private fun getNews() {
        view.setLoadingProgressBarVisible()
        if (NetworkUtils.isNetworkAvailable(mApplication.applicationContext)) {
            mRetrofitServices.getService(NewsService::class.java).getNewsByLimit(OFFSET, LIMIT).enqueue(
                    networkCallback {
                        onResponseSuccessful {
                            mUserSession.userId.apply { view.setAdapterUserID(mUserSession.userId!!) }
                            view.setLoadingProgressBarGone()
                            view.setNoContentImageGone()
                            listNews.clear()
                            listNews.addAll(it!!)
                            view.showNews(listNews)
                            calculateOffset()
                        }
                        onResponseFailed { _, _ -> view.showError() }
                        onCallFailure { view.showError() }
                    }
            )
        } else {
            view.setLoadingProgressBarGone()
            view.showConnectionError()
            if (listNews.isNullOrEmpty()) { view.setNoContentImageVisible() }
        }
    }

    fun loadMoreNews() {
        view.setLoadingProgressBarVisible()
        if (NetworkUtils.isNetworkAvailable(mApplication.applicationContext)) {
            mRetrofitServices.getService(NewsService::class.java).getNewsByLimit(OFFSET, LIMIT).enqueue(
                    networkCallback {
                        onResponseSuccessful {
                            view.setLoadingProgressBarGone()
                            listNews.addAll(it!!)
                            view.showNews(listNews)
                            calculateOffset()
                        }
                        onResponseFailed { _, _ -> view.showError() }
                        onCallFailure { view.showError() }
                    }
            )
        } else {
            view.setLoadingProgressBarGone()
            view.showConnectionError()
        }
    }

    fun onSelectedItem(position: Int) {
        view.onItemNewClicked(listNews[position])
    }

    fun onReceivedLikeEvent(new: New) {
        listNews.mapIndexed { index, n ->
            if (n.id == new.id && n.likes != new.likes) {
                listNews[index] = n.copy(likes = new.likes)
                view.showNews(listNews)
            }
        }
    }

    private fun calculateOffset() {
        OFFSET = (PAGE - 1) * (LIMIT + 1)
        PAGE++
    }

    private fun restoreOffset() {
        OFFSET = 0
        PAGE = 1
    }

    companion object {
        private const val LIMIT = 10
        private var OFFSET = 0
        private var PAGE = 1
    }
}