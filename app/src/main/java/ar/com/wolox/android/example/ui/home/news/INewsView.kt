package ar.com.wolox.android.example.ui.home.news

import android.widget.ImageView
import ar.com.wolox.android.example.model.New

interface INewsView {

    fun setNoContentImageVisible()

    fun setNoContentImageGone()

    fun setLoadingProgressBarVisible()

    fun setLoadingProgressBarGone()

    fun showNews(news: List<New>)

    fun showError()

    fun showConnectionError()

    fun setAdapterUserID(userId: String)

    fun onItemNewClicked(new: New, viewLike: ImageView)
}