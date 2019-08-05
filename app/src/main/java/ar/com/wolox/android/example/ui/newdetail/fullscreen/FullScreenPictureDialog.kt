package ar.com.wolox.android.example.ui.newdetail.fullscreen

import android.os.Bundle
import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.fragment.WolmoDialogFragment
import kotlinx.android.synthetic.main.dialog_fullscreen_picture.vFullScreenPicture
import ar.com.wolox.android.example.utils.Extras.News.NEW_PICTURE as NEW_PICTURE

class FullScreenPictureDialog : WolmoDialogFragment<FullScreenPresenter>(), IFullScreenPictureView {

    override fun layout(): Int = R.layout.dialog_fullscreen_picture

    override fun init() {
    }

    override fun handleArguments(arguments: Bundle?): Boolean {
        return if (arguments != null) {
            arguments.getString(NEW_PICTURE)?.apply {
                presenter.onLoadedImageUrl(this)
            }
            true
        } else {
            false
        }
    }

    override fun showFullScreenPicture(imageUrl: String) {
        vFullScreenPicture.setImageURI((imageUrl).replace(requireActivity().getString(R.string.reg_pattern_protocol).toRegex(),
                requireActivity().getString(R.string.allow_protocol)))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window?.attributes?.windowAnimations = DIALOG_ANIMATION
    }

    companion object {
        private const val DIALOG_ANIMATION = R.style.DialogAnimation
    }
}