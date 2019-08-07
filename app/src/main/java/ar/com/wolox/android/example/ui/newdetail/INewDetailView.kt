package ar.com.wolox.android.example.ui.newdetail

import ar.com.wolox.android.example.model.New

interface INewDetailView {

    fun showNewDetail(new: New)

    fun setLikeIcon(isLiked: Boolean)

    fun showError()

    fun showConnectionError()

    fun showFullScreenPicture(imageUrl: String)

    fun setLoadingProgressBarVisible()

    fun setLoadingProgressBarGone()

    fun setLikeIconEnable()

    fun setLikeIconDisable()
}