package ar.com.wolox.android.example.utils

import ar.com.wolox.android.example.model.New

class Events {

    companion object ShareLike {
        private lateinit var likes: New
        fun ShareLike(likes: New) {
            this.likes = likes
        }
        fun getLike(): New = likes
    }
}