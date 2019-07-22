package ar.com.wolox.android.example.ui.login

interface ILoginView {

    fun onEmptyForm()

    fun onWrongUsernameFormat()

    fun onUserLoggedIn()
}