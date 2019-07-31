package ar.com.wolox.android.example.ui.home

import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class HomePagePresenter @Inject constructor() : BasePresenter<IHomePageView>() {

    fun setSelectedViewPager(position: Int) {
        view.onSelectedViewPager(position)
    }
}