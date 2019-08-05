package ar.com.wolox.android.example.ui.newdetail

import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.New
import ar.com.wolox.android.example.ui.newdetail.fullscreen.FullScreenPictureDialog
import ar.com.wolox.android.example.utils.Extras.News.NEW
import ar.com.wolox.android.example.utils.Extras.News.NEW_PICTURE
import ar.com.wolox.android.example.utils.Extras.News.TAG_FULLSCREEN_PICTURE
import ar.com.wolox.android.example.utils.formatDateToTime
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.util.ToastFactory
import kotlinx.android.synthetic.main.fragment_new_detail.*
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class NewDetailFragment : WolmoFragment<NewDetailPresenter>(), INewDetailView {

    @Inject internal lateinit var toastFactory: ToastFactory

    override fun layout(): Int = R.layout.fragment_new_detail

    override fun init() {
        val defaultColor = ContextCompat.getColor(requireActivity() as Context, DEFAULT_PROGRESS_COLOR)
        vNewDetailSwipe.setColorSchemeColors(defaultColor, defaultColor, defaultColor)
    }

    override fun handleArguments(arguments: Bundle?): Boolean {
        if (arguments != null) {
            (arguments.getSerializable(NEW) as New).apply {
                presenter.loadReceivedNew(this)
                return true
            }
        } else {
            return false
        }
    }

    override fun setListeners() {
        super.setListeners()
        vNewDetailBackButton.setOnClickListener { requireActivity().onBackPressed() }
        vNewDetailSwipe.setOnRefreshListener { presenter.refreshNew() }
        vNewDetailLikeIcon.setOnClickListener { presenter.onLikeClicked() }
        vNewDetailPicture.setOnClickListener { presenter.onPictureClicked() }
    }

    override fun showNewDetail(new: New) {
        vNewDetailTitle.text = new.title
        vNewDetailText.text = new.text
        vNewDetailTime.text = formatDateToTime(requireContext(), new.createdAt)
        vNewDetailPicture.setImageURI((new.picture).replace(requireActivity().getString(R.string.reg_pattern_protocol).toRegex(),
                requireActivity().getString(R.string.allow_protocol)))
        EventBus.getDefault().post(new)
    }

    override fun setLikeIcon(isLiked: Boolean) {
        vNewDetailLikeIcon.background = when (isLiked) {
            true -> ContextCompat.getDrawable(requireContext(), ICON_LIKE_ON)
            false -> ContextCompat.getDrawable(requireContext(), ICON_LIKE_OFF)
        }
    }

    override fun setLoadingProgressBarVisible() {
        vNewDetailSwipe.isRefreshing = true
    }

    override fun setLoadingProgressBarGone() {
        vNewDetailSwipe.isRefreshing = false
    }

    override fun showError() {
        toastFactory.show(R.string.unknown_error)
    }

    override fun showConnectionError() {
        toastFactory.show(R.string.network_error_message)
    }

    override fun setLikeIconEnable() {
        vNewDetailLikeIcon.isEnabled = true
    }

    override fun setLikeIconDisable() {
        vNewDetailLikeIcon.isEnabled = false
    }

    override fun showFullScreenPicture(imageUrl: String) {
        val fullScreenPictureDialog = FullScreenPictureDialog()
        val bundle = Bundle()
        bundle.putString(NEW_PICTURE, imageUrl)
        fullScreenPictureDialog.arguments = bundle
        fullScreenPictureDialog.show(fragmentManager!!.beginTransaction(), TAG_FULLSCREEN_PICTURE)
    }

    companion object {
        private const val ICON_LIKE_ON = R.drawable.ic_like_on_large
        private const val ICON_LIKE_OFF = R.drawable.ic_like_off_large
        private const val DEFAULT_PROGRESS_COLOR = R.color.colorAccent
    }
}