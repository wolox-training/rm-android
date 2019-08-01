package ar.com.wolox.android.example.ui.home.news

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.New
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.util.ToastFactory
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class NewsFragment @Inject constructor() : WolmoFragment<NewsPresenter>(), INewsView {

    @Inject internal lateinit var toastFactory: ToastFactory

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()

    override fun layout(): Int = R.layout.fragment_news

    override fun init() {
        val defaultColor = ContextCompat.getColor(requireActivity() as Context, DEFAULT_PROGRESS_COLOR)
        vNewsSwipeContainer.setColorSchemeColors(defaultColor, defaultColor, defaultColor)
        newsAdapter = NewsAdapter()
        linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        vNewsRecycler.layoutManager = linearLayoutManager
        vNewsRecycler.adapter = newsAdapter
    }

    override fun setListeners() {
        vNewsSwipeContainer.setOnRefreshListener { presenter.refreshNews() }
        vNewsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (!vNewsSwipeContainer.isRefreshing && (lastVisibleItemPosition + 1 == totalItemCount)) {
                    presenter.loadMoreNews()
                }
            }
        })
    }

    override fun setNoContentImageVisible() {
        vNewsNoContentImage.visibility = View.VISIBLE
    }

    override fun setNoContentImageGone() {
        vNewsNoContentImage.visibility = View.GONE
    }

    override fun setLoadingProgressBarVisible() {
        vNewsSwipeContainer.isRefreshing = true
    }

    override fun setLoadingProgressBarGone() {
        vNewsSwipeContainer.isRefreshing = false
    }

    override fun showNews(news: List<New>) {
        newsAdapter.submitList(news)
        newsAdapter.notifyDataSetChanged()
    }

    override fun showError() {
        toastFactory.show(R.string.unknown_error)
    }

    override fun showConnectionError() {
        toastFactory.show(R.string.network_error_message)
    }

    override fun setAdapterUserID(userId: String) {
        newsAdapter.setUserId(userId)
    }

    companion object {
        private const val DEFAULT_PROGRESS_COLOR = R.color.colorAccent
    }
}