package ar.com.wolox.android.example.ui.home.youtube.videoPlayer

import android.os.Bundle
import android.util.Log
import ar.com.wolox.android.R
import ar.com.wolox.android.example.BaseConfiguration
import ar.com.wolox.android.example.utils.Extras.Youtube.VIDEO_ID
import ar.com.wolox.wolmo.core.util.ToastFactory
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.fragment_video_player.vYotubeVideoPlayer
import javax.inject.Inject

class VideoPlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    @Inject lateinit var toastFactory: ToastFactory
    private lateinit var videoId: String
    private lateinit var youtubePlayer: YouTubePlayer

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        setContentView(R.layout.fragment_video_player)
        toastFactory = ToastFactory(this)
        initVideo()
    }

    private fun initVideo() {
        intent.getStringExtra(VIDEO_ID)?.let {
            videoId = it
        } ?: run {
            toastFactory.show(R.string.unknown_error)
            finish()
        }
        vYotubeVideoPlayer.initialize(BaseConfiguration.INTERCEPTOR_YOUTUBE_KEY_VALUE, this)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
        player?.apply {
            youtubePlayer = this
            setPlayerStateChangeListener(playerStateChangeListener)
            setPlaybackEventListener(playbackEventListener)

            if (!wasRestored) {
                setFullscreen(true)
                setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                loadVideo(videoId)
                play()
            }
        }
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
        toastFactory.show(R.string.unknown_error)
    }

    private val playerStateChangeListener = object : YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
            Log.d(LOG_TAG, ON_ADD_STARTED)
        }

        override fun onLoading() {
            Log.d(LOG_TAG, ON_LOADING)
        }

        override fun onVideoStarted() {
            Log.d(LOG_TAG, ON_VIDEO_STARTED)
        }

        override fun onLoaded(p0: String?) {
            Log.d(LOG_TAG, ON_LOADED)
        }

        override fun onVideoEnded() {
            Log.d(LOG_TAG, ON_VIDEO_ENDED)
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
            toastFactory.show(R.string.unknown_error)
        }
    }

    private val playbackEventListener = object : YouTubePlayer.PlaybackEventListener {
        override fun onSeekTo(p0: Int) {
            Log.d(LOG_TAG, ON_SEEK_TO)
        }

        override fun onBuffering(p0: Boolean) {
            Log.d(LOG_TAG, ON_BUFFERING)
        }

        override fun onPlaying() {
            Log.d(LOG_TAG, ON_PLAYING)
        }

        override fun onStopped() {
            Log.d(LOG_TAG, ON_STOPPED)
        }

        override fun onPaused() {
            Log.d(LOG_TAG, ON_PAUSED)
        }
    }

    companion object {
        private const val LOG_TAG = "VideoPlayerActivity"
        private const val ON_ADD_STARTED = "onAdStarted"
        private const val ON_LOADING = "onLoading"
        private const val ON_VIDEO_STARTED = "onVideoStarted"
        private const val ON_LOADED = "onLoaded"
        private const val ON_VIDEO_ENDED = "onVideoEnded"
        private const val ON_SEEK_TO = "onSeekTo"
        private const val ON_BUFFERING = "onBuffering"
        private const val ON_PLAYING = "onPlaying"
        private const val ON_STOPPED = "onStopped"
        private const val ON_PAUSED = "onPaused"
    }

    override fun onDestroy() {
        super.onDestroy()
        youtubePlayer.release()
    }
}