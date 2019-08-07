package ar.com.wolox.android.example.utils

import android.app.Activity
import android.content.SharedPreferences
import androidx.fragment.app.Fragment

/**
 * Util class to store keys to use with [SharedPreferences] or as argument between
 * [Fragment] or [Activity].
 */
object Extras {

    object UserLogin {
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val USERID = "userid"
        const val LOGGED_TYPE = "loggedType"
        const val LOGGED_APP = "native"
        const val LOGGED_GOOGLE = "google"
        const val RC_GOOGLE_SIGN_IN = 101
    }
}
