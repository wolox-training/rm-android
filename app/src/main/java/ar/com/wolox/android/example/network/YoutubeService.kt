package ar.com.wolox.android.example.network

import ar.com.wolox.android.example.model.youtube.YoutubeVideo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeService {

    @GET("search")
    fun searchVideo(@Query("q") searchText: String): Call<YoutubeVideo>

    @GET("search")
    fun searchMoreVideos(@Query("pageToken") pageToken: String): Call<YoutubeVideo>
}