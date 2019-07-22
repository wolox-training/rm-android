package ar.com.wolox.android.example.ui.login

import android.util.Log
import ar.com.wolox.android.example.utils.UserSession
import ar.com.wolox.wolmo.core.presenter.BasePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor(private val mUserSession: UserSession) : BasePresenter<ILoginView>() {

    fun startLogin(username: String, password: String) {
        if (mUserSession.username.isNullOrEmpty() || mUserSession.password.isNullOrEmpty()) {
            if (username.isEmpty() || password.isEmpty()) {
                view.onEmptyForm()
            } else if (!evaluateUsername(username)) {
                view.onWrongUsernameFormat()
            } else {
                mUserSession.username = username
                mUserSession.password = password
                view.onUserLoggedIn()
            }
        } else {
            Log.d(this.javaClass.simpleName, "already logged in")
        }
    }

    private fun evaluateUsername(username: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }
}
