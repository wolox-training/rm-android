package ar.com.wolox.android.example.ui.login

import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.activity.WolmoActivity

class LoginActivity : WolmoActivity() {

    override fun init() {
        replaceFragment(R.id.vActivityBaseContent, LoginFragment())
    }

    override fun layout(): Int = R.layout.activity_base
}