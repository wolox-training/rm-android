package ar.com.wolox.android.example.ui.home.news

import ar.com.wolox.android.example.model.New

interface INewsView {

    fun setNoContentImageVisible()

    fun setNoContentImageGone()

    fun setLoadingProgressBarVisible()

    fun setLoadingProgressBarGone()

    fun showNews(news: List<New>)
}