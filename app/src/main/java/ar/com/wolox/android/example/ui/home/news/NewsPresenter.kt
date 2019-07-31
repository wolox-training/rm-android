package ar.com.wolox.android.example.ui.home.news

import ar.com.wolox.android.example.model.New
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class NewsPresenter @Inject constructor() : BasePresenter<INewsView>() {

    fun getNews() {
        view.setNoContentImageVisible()
    }

    fun refreshNews() {
        view.setNoContentImageGone()
        view.showNews(FAKE_NEWS)
        view.setLoadingProgressBarGone()
    }

    companion object {
        private val FAKE_NEWS = listOf(New("1", "Fake title", "Fake content", "image"),
                New("2", "Fake title 2", "Fake content 2", "image"))
    }
}