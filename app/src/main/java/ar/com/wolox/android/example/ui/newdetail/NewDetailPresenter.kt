package ar.com.wolox.android.example.ui.newdetail

import android.app.Application
import ar.com.wolox.android.example.model.New
import ar.com.wolox.android.example.network.NewsService
import ar.com.wolox.android.example.utils.NetworkUtils
import ar.com.wolox.android.example.utils.UserSession
import ar.com.wolox.android.example.utils.networkCallback
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices
import javax.inject.Inject

class NewDetailPresenter @Inject constructor(
    private val retrofitServices: RetrofitServices,
    private val userSession: UserSession,
    private val application: Application
) : BasePresenter<INewDetailView>() {

    private lateinit var new: New

    fun loadReceivedNew(new: New) {
        this.new = new
    }

    override fun onViewAttached() {
        super.onViewAttached()
        showNews(new)
    }

    private fun showNews(new: New) {
        view.setElementTransition(new.id.toString())
        view.showNewDetail(new)
        view.setLikeIcon(userSession.userId!!.toInt() in new.likes)
    }

    fun refreshNew() {
        view.setLoadingProgressBarVisible()
        if (NetworkUtils.isNetworkAvailable(application.applicationContext)) {
            retrofitServices.getService(NewsService::class.java).getNewsById(new.id).enqueue(
                    networkCallback {
                        onResponseSuccessful {
                            showNews(it!!)
                            view.setLoadingProgressBarGone()
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

    fun onLikeClicked() {
        view.setLikeIconDisable()
        if (NetworkUtils.isNetworkAvailable(application.applicationContext)) {
            switchLike()
            retrofitServices.getService(NewsService::class.java).setNewLike(new.id, new).enqueue(
                    networkCallback {
                        onResponseSuccessful {
                            showNews(it!!)
                            view.setLikeIconEnable()
                        }
                        onResponseFailed { _, _ ->
                            view.showError()
                            view.setLikeIconEnable()
                        }
                        onCallFailure {
                            view.showError()
                            view.setLikeIconEnable()
                        }
                    }
            )
        } else {
            view.showConnectionError()
            view.setLikeIconEnable()
        }
    }

    fun onPictureClicked() {
        view.showFullScreenPicture(new.picture)
    }

    private fun switchLike() {
        if (userSession.userId!!.toInt() in new.likes) {
            (new.likes as MutableList<Int>).remove(userSession.userId!!.toInt())
        } else {
            (new.likes as MutableList<Int>).add(userSession.userId!!.toInt())
        }
    }
}