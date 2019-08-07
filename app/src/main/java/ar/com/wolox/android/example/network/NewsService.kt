package ar.com.wolox.android.example.network

import ar.com.wolox.android.example.model.New
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsService {

    @GET("/news")
    fun getNewsByLimit(@Query("offset") offset: Int, @Query("limit") limit: Int): Call<List<New>>

    @GET("/news/{newID}")
    fun getNewsById(@Path("newID") newId: Int): Call<New>

    @PUT("/news/{newID}")
    fun setNewLike(@Path("newID") newId: Int, @Body new: New): Call<New>
}