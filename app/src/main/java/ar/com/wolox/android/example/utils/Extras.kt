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
    }

    object News {
        const val NEW = "new"
        const val NEW_PICTURE = "picture"
        const val TAG_FULLSCREEN_PICTURE = "fullscreen_picture"
    }
}
