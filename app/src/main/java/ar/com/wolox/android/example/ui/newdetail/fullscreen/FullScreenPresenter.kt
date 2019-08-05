package ar.com.wolox.android.example.ui.newdetail.fullscreen

import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class FullScreenPresenter @Inject constructor() : BasePresenter<IFullScreenPictureView>() {

    private lateinit var imageUrl: String

    fun onLoadedImageUrl(imageUrl: String) {
        this.imageUrl = imageUrl
    }

    override fun onViewAttached() {
        super.onViewAttached()
        view.showFullScreenPicture(imageUrl)
    }
}