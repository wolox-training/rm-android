package ar.com.wolox.android.example.ui.home

import androidx.core.content.ContextCompat
import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_homepage.*

class HomePageFragment : WolmoFragment<HomePagePresenter>(), IHomePageView {

    override fun init() {
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
    }
}