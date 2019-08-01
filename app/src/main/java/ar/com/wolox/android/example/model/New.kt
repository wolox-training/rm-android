package ar.com.wolox.android.example.model

import com.google.gson.annotations.SerializedName

data class New(
    @SerializedName("id")val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("title") val title: String,
    @SerializedName("picture") val picture: String,
    @SerializedName("text") val text: String,
    @SerializedName("likes") val likes: List<Int>
)