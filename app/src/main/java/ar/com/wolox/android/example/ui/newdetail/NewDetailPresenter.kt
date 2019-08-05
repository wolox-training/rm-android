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

class NewDetailPresenter @Inject constructor(private val mRetrofitServices: RetrofitServices, private val mUserSession: UserSession, private val mApplication: Application) : BasePresenter<INewDetailView>() {

    private lateinit var new: New

    fun loadReceivedNew(new: New) {
        this.new = new
        view.showNewDetail(new)
        view.setLikeIcon(mUserSession.userId!!.toInt() in new.likes)
    }

    fun refreshNew() {
        view.setLoadingProgressBarVisible()
        if (NetworkUtils.isNetworkAvailable(mApplication.applicationContext)) {
            mRetrofitServices.getService(NewsService::class.java).getNewsById(new.id).enqueue(
                    networkCallback {
                        onResponseSuccessful {
                            loadReceivedNew(it!!)
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
        if (NetworkUtils.isNetworkAvailable(mApplication.applicationContext)) {
            switchLike()
            mRetrofitServices.getService(NewsService::class.java).setNewLike(new.id, new).enqueue(
                    networkCallback {
                        onResponseSuccessful {
                            loadReceivedNew(it!!)
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
        if (mUserSession.userId!!.toInt() in new.likes) {
            (new.likes as MutableList<Int>).remove(mUserSession.userId!!.toInt())
        } else {
            (new.likes as MutableList<Int>).add(mUserSession.userId!!.toInt())
        }
    }
}