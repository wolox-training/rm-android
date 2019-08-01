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
    private var offset = 0
    private var page = 1

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
            mRetrofitServices.getService(NewsService::class.java).getNewsByLimit(offset, LIMIT).enqueue(
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
            mRetrofitServices.getService(NewsService::class.java).getNewsByLimit(offset, LIMIT).enqueue(
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

    private fun calculateOffset() {
        offset = (page - 1) * (LIMIT + 1)
        page++
    }

    private fun restoreOffset() {
        offset = 0
        page = 1
    }

    companion object {
        private const val LIMIT = 10
    }
}