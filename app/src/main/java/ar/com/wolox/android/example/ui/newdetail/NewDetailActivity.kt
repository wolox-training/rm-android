package ar.com.wolox.android.example.ui.newdetail

import android.os.Bundle
import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.activity.WolmoActivity
import ar.com.wolox.android.example.utils.Extras.News.NEW as NEW

class NewDetailActivity : WolmoActivity() {

    override fun layout(): Int = R.layout.activity_base

    override fun init() {
        val fragment = NewDetailFragment()
        if (handleArguments(intent.extras)) {
            val bundle = Bundle()
            intent.getSerializableExtra(NEW).apply {
                bundle.putSerializable(NEW, this)
                fragment.arguments = bundle
            }
        }
        replaceFragment(R.id.vActivityBaseContent, fragment)
    }
}