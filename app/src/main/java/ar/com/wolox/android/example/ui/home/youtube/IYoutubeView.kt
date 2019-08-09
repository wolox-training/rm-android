package ar.com.wolox.android.example.ui.home.youtube

import ar.com.wolox.android.example.model.youtube.Item

interface IYoutubeView {

    fun hidePlaceholder()

    fun showPlaceholder()

    fun hideKeyBoard()

    fun showProgressBar()

    fun hideProgressBar()

    fun onYoutubeSearchReceived(videoList: List<Item>)

    fun onItemVideoClicked(videoId: String)

    fun showError()

    fun showEmptySearchError()
}