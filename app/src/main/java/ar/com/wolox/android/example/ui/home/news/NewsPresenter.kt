package ar.com.wolox.android.example.ui.home.news

import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class NewsPresenter @Inject constructor() : BasePresenter<INewsView>() {

    fun getNews() {
        view.setNoContentImageVisible()
    }

    fun refreshNews() {
        view.setNoContentImageGone()
        view.setLoadingProgressBarVisible()
    }
}