package ar.com.wolox.android.example

internal open class BaseConfiguration {

    companion object {
        const val TRAINING_CONFIGURATION_URL = "https://android-training.herokuapp.com"
        const val YOUTUBE_CONFIGURATION_URL = "https://www.googleapis.com/youtube/v3/"
        const val SHARED_PREFERENCES_NAME = "private-shared-prefs"
        const val TERMS_CONDITIONS_URL = "https://www.wolox.com.ar"
        const val INTERCEPTOR_YOUTUBE_KEY = "key"
        const val INTERCEPTOR_YOUTUBE_KEY_VALUE = "AIzaSyAGec5YpBXF1MsDrpUu-pcvg3j6-gPisYc"
        const val INTERCEPTOR_YOUTUBE_PART = "part"
        const val INTERCEPTOR_YOUTUBE_PART_VALUE = "snippet"
        const val INTERCEPTOR_YOUTUBE_TYPE = "type"
        const val INTERCEPTOR_YOUTUBE_TYPE_VALUE = "video"
        const val INTERCEPTOR_YOUTUBE_MAX_RESULTS = "maxResults"
        const val INTERCEPTOR_YOUTUBE_MAX_RESULTS_VALUE = "12"
    }
}
