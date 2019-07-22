package ar.com.wolox.android.example.ui.login

import android.util.Log
import ar.com.wolox.android.R
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : WolmoFragment<LoginPresenter>(), ILoginView {

    override fun init() {
    }

    override fun layout(): Int = R.layout.fragment_login

    override fun setListeners() {
        vLoginButton.setOnClickListener {
            presenter.startLogin(vUsername?.text.toString(), vPassword?.text.toString())
        }
    }

    override fun onEmptyForm() {
        vUsername?.let {
            it.text.ifBlank { it.setError(resources.getString(R.string.login_required_field)) }
        }
        vPassword?.let {
            it.text.ifBlank { it.setError(resources.getString(R.string.login_required_field)) }
        }
    }

    override fun onWrongUsernameFormat() {
        vUsername?.setError(resources.getString(R.string.login_wrong_username_format))
    }

    override fun onUserLoggedIn() {
        Log.d(this.javaClass.simpleName, "logged in")
    }
}