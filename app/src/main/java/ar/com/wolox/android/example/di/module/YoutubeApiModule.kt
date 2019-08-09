package ar.com.wolox.android.example.di.module

import androidx.annotation.NonNull
import ar.com.wolox.android.example.BaseConfiguration
import ar.com.wolox.android.example.network.YoutubeService
import ar.com.wolox.android.example.network.interceptor.YoutubeApiInterceptor
import ar.com.wolox.wolmo.core.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class YoutubeApiModule {

    @Provides
    @ApplicationScope
    fun provideHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(YoutubeApiInterceptor())
                    .build()

    @Provides
    @ApplicationScope
    fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BaseConfiguration.YOUTUBE_CONFIGURATION_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    @Provides
    @ApplicationScope
    fun provideYoutubeService(@NonNull retrofit: Retrofit): YoutubeService =
            retrofit.create(YoutubeService::class.java)
}