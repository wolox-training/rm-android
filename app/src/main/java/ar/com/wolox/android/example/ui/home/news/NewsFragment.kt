package ar.com.wolox.android.example.ui.home.news

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.New
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class NewsFragment @Inject constructor() : WolmoFragment<NewsPresenter>(), INewsView {

    private lateinit var mNewsAdapter: NewsAdapter

    override fun init() {
        val defaultColor = ContextCompat.getColor(requireActivity(), DEFAULT_PROGRESS_COLOR)
        vNewsSwipeContainer.setColorSchemeColors(defaultColor, defaultColor, defaultColor)
        mNewsAdapter = NewsAdapter()
        vNewsRecycler.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        vNewsRecycler.adapter = mNewsAdapter
        presenter.getNews()
    }

    override fun layout(): Int = R.layout.fragment_news

    override fun setListeners() {
        vNewsSwipeContainer.setOnRefreshListener {
            presenter.refreshNews()
        }
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
        mNewsAdapter.addAllNews(news)
    }

    companion object {
        private const val DEFAULT_PROGRESS_COLOR = R.color.colorAccent
    }
}