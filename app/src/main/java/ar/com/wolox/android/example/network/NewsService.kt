package ar.com.wolox.android.example.network

import ar.com.wolox.android.example.model.New
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("/news")
    fun getNewsByLimit(@Query("offset") offset: Int, @Query("limit") limit: Int): Call<List<New>>
}