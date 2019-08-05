package ar.com.wolox.android.example.ui.newdetail.fullscreen

import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class FullScreenPresenter @Inject constructor() : BasePresenter<IFullScreenPictureView>() {

    fun onLoadedImageUrl(imageUrl: String) {
        view.showFullScreenPicture(imageUrl)
    }
}