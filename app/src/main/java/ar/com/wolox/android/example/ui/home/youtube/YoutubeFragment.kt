package ar.com.wolox.android.example.ui.home.youtube

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.youtube.Item
import ar.com.wolox.android.example.ui.home.youtube.videoPlayer.VideoPlayerActivity
import ar.com.wolox.android.example.utils.Extras.Youtube.VIDEO_ID
import ar.com.wolox.android.example.utils.addOnItemClickListener
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.util.ToastFactory
import kotlinx.android.synthetic.main.fragment_youtube.vYoutubePlaceholder
import kotlinx.android.synthetic.main.fragment_youtube.vYoutubeProgressBar
import kotlinx.android.synthetic.main.fragment_youtube.vYoutubeRecycler
import kotlinx.android.synthetic.main.fragment_youtube.vYoutubeSearchButton
import kotlinx.android.synthetic.main.fragment_youtube.vYoutubeSearchText
import javax.inject.Inject

class YoutubeFragment @Inject constructor() : WolmoFragment<YoutubePresenter>(), IYoutubeView {

    @Inject internal lateinit var toastFactory: ToastFactory

    private lateinit var youtubeAdapter: YoutubeAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()

    override fun layout(): Int = R.layout.fragment_youtube

    override fun init() {
        showPlaceholder()
        youtubeAdapter = YoutubeAdapter()
        linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        vYoutubeRecycler.layoutManager = linearLayoutManager
        vYoutubeRecycler.adapter = youtubeAdapter
    }

    override fun setListeners() {
        vYoutubeSearchButton.setOnClickListener {
            presenter.onSearchButtonClicked(vYoutubeSearchText.text.toString())
        }
        vYoutubeRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (vYoutubeProgressBar.visibility == View.GONE &&
                        (lastVisibleItemPosition + 1 == totalItemCount)) {
                    presenter.loadMoreVideos()
                }
            }
        })

        vYoutubeRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                presenter.onSelectedItem(position)
            }
        })
    }

    override fun hidePlaceholder() {
        vYoutubePlaceholder.visibility = View.GONE
    }

    override fun showPlaceholder() {
        vYoutubePlaceholder.visibility = View.VISIBLE
    }

    override fun onYoutubeSearchReceived(videoList: List<Item>) {
        youtubeAdapter.submitList(videoList)
        youtubeAdapter.notifyDataSetChanged()
    }

    override fun onItemVideoClicked(videoId: String) {
        val intent = Intent(requireActivity(), VideoPlayerActivity::class.java)
        intent.putExtra(VIDEO_ID, videoId)
        startActivity(intent)
    }

    override fun hideKeyBoard() {
        (requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
        }
    }

    override fun showProgressBar() {
        vYoutubeProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        vYoutubeProgressBar.visibility = View.GONE
    }

    override fun showError() {
        toastFactory.show(R.string.unknown_error)
    }

    override fun showEmptySearchError() {
        toastFactory.show(R.string.fragment_youtube_empty_search_text)
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }
}