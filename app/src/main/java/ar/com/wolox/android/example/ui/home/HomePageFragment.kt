package ar.com.wolox.android.example.ui.home

import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import ar.com.wolox.android.R
import ar.com.wolox.android.example.ui.home.news.NewsFragment
import ar.com.wolox.android.example.ui.home.profile.ProfileFragment
import ar.com.wolox.wolmo.core.adapter.viewpager.SimpleFragmentPagerAdapter
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_homepage.*
import javax.inject.Inject

class HomePageFragment : WolmoFragment<HomePagePresenter>(), IHomePageView {

    @Inject internal lateinit var pageNews: NewsFragment
    @Inject internal lateinit var pageProfile: ProfileFragment
    private lateinit var fragmentPagerAdapter: SimpleFragmentPagerAdapter

    override fun init() {
        fragmentPagerAdapter = SimpleFragmentPagerAdapter(childFragmentManager)
        fragmentPagerAdapter.addFragments(
                Pair<Fragment, String>(pageNews, TITLE_NEWS),
                Pair<Fragment, String>(pageProfile, TITLE_PROFILE))
        vHomeViewPager.adapter = fragmentPagerAdapter

        vTabSelection.getTabAt(DEFAULT_TAB)?.let {
            it.icon = ContextCompat.getDrawable(requireActivity(), getSelectIcon(DEFAULT_TAB))
        }
    }

    override fun layout(): Int = R.layout.fragment_homepage

    override fun setListeners() {
        vTabSelection.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                p0?.position.let {
                    p0?.icon = ContextCompat.getDrawable(requireActivity(), getUnSelectIcon(it))
                }
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                p0?.position.let {
                    presenter.setSelectedViewPager(it ?: DEFAULT_TAB)
                    p0?.icon = ContextCompat.getDrawable(requireActivity(), getSelectIcon(it))
                }
            }
        })
    }

    private fun getSelectIcon(position: Int?): Int {
        return when (position) {
            NEWS_TAB -> NEWS_ACTIVE_TAB
            PROFILE_TAB -> PROFILE_ACTIVE_TAB
            else -> DEFAULT_TAB
        }
    }

    private fun getUnSelectIcon(position: Int?): Int {
        return when (position) {
            NEWS_TAB -> NEWS_INACTIVE_TAB
            PROFILE_TAB -> PROFILE_INACTIVE_TAB
            else -> DEFAULT_TAB
        }
    }

    companion object {
        private const val DEFAULT_TAB = 0
        private const val NEWS_TAB = 0
        private const val PROFILE_TAB = 1
        private const val NEWS_ACTIVE_TAB = R.drawable.ic_news_list_on
        private const val NEWS_INACTIVE_TAB = R.drawable.ic_news_list_off
        private const val PROFILE_ACTIVE_TAB = R.drawable.ic_profile_on
        private const val PROFILE_INACTIVE_TAB = R.drawable.ic_profile_off
        private const val TITLE_NEWS = "News"
        private const val TITLE_PROFILE = "Profile"
    }

    override fun onSelectedViewPager(position: Int) {
        vHomeViewPager.currentItem = position
        vHomeViewPager.adapter?.notifyDataSetChanged()
    }
}