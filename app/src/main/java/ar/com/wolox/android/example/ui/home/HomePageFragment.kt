package ar.com.wolox.android.example.ui.home

import androidx.core.content.ContextCompat
import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_homepage.*

class HomePageFragment : WolmoFragment<HomePagePresenter>(), IHomePageView {

    override fun init() {
        vTabSelection.getTabAt(0)?.let {
            it.icon = ContextCompat.getDrawable(activity!!, getSelectIcon(0))
        }
    }

    override fun layout(): Int {
        return R.layout.fragment_homepage
    }

    override fun setListeners() {
        vTabSelection.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                p0?.position.let {
                    p0?.icon = ContextCompat.getDrawable(activity!!, getUnSelectIcon(it))
                }
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                p0?.position.let {
                    p0?.icon = ContextCompat.getDrawable(activity!!, getSelectIcon(it))
                }
            }
        })
    }

    private fun getSelectIcon(position: Int?): Int {
        return when (position) {
            0 -> R.drawable.ic_news_list_on
            1 -> R.drawable.ic_profile_on
            else -> 0
        }
    }

    private fun getUnSelectIcon(position: Int?): Int {
        return when (position) {
            0 -> R.drawable.ic_news_list_off
            1 -> R.drawable.ic_profile_off
            else -> 0
        }
    }
}