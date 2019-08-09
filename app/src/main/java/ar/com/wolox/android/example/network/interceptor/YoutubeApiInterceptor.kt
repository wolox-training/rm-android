package ar.com.wolox.android.example.network.interceptor

import ar.com.wolox.android.BuildConfig
import ar.com.wolox.android.example.BaseConfiguration.Companion.INTERCEPTOR_YOUTUBE_KEY
import ar.com.wolox.android.example.BaseConfiguration.Companion.INTERCEPTOR_YOUTUBE_MAX_RESULTS
import ar.com.wolox.android.example.BaseConfiguration.Companion.INTERCEPTOR_YOUTUBE_MAX_RESULTS_VALUE
import ar.com.wolox.android.example.BaseConfiguration.Companion.INTERCEPTOR_YOUTUBE_PART
import ar.com.wolox.android.example.BaseConfiguration.Companion.INTERCEPTOR_YOUTUBE_PART_VALUE
import ar.com.wolox.android.example.BaseConfiguration.Companion.INTERCEPTOR_YOUTUBE_TYPE
import ar.com.wolox.android.example.BaseConfiguration.Companion.INTERCEPTOR_YOUTUBE_TYPE_VALUE
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class YoutubeApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val originalUrl = originalRequest.url()
        val url: HttpUrl = originalUrl.newBuilder()
                .addQueryParameter(INTERCEPTOR_YOUTUBE_KEY, BuildConfig.YouTubeApiKey)
                .addQueryParameter(INTERCEPTOR_YOUTUBE_MAX_RESULTS, INTERCEPTOR_YOUTUBE_MAX_RESULTS_VALUE)
                .addQueryParameter(INTERCEPTOR_YOUTUBE_PART, INTERCEPTOR_YOUTUBE_PART_VALUE)
                .addQueryParameter(INTERCEPTOR_YOUTUBE_TYPE, INTERCEPTOR_YOUTUBE_TYPE_VALUE)
                .build()
        val requestBuilder: Request.Builder = originalRequest.newBuilder().url(url)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}