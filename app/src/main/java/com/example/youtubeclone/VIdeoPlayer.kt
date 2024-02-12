package com.example.youtubeclone

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.material.slider.Slider


class VIdeoPlayer : AppCompatActivity() {
    private var player: SimpleExoPlayer? = null
    private lateinit var playerView: PlayerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        playerView = findViewById(R.id.playerView)

        val slider = findViewById<Slider>(R.id.exo_bottom_bar)

        // Set up listeners to track the video progress
        slider.addOnChangeListener { slider, value, fromUser ->
            // Update video playback based on slider value
            if (fromUser) {
                // Seek to the corresponding position in the video
                val newPosition = value.toLong()
                player?.seekTo(newPosition)
            }
        }

        // Set up listener to update slider value as video progresses
        player?.addListener(object : Player.Listener {
            override fun onPositionDiscontinuity(reason: Int) {
                super.onPositionDiscontinuity(reason)
                // Update slider value to reflect current position in video
                slider.value = player?.currentPosition?.toFloat() ?: 0f
            }
        })

        // Initialize the player
        player = SimpleExoPlayer.Builder(this, DefaultRenderersFactory(this))
            .setTrackSelector(DefaultTrackSelector(this))
            .setLoadControl(DefaultLoadControl())
            .build()

        // Attach the player to the view
        playerView.player = player

        // Prepare the media source
        val mediaSource = buildMediaSource()

        // Prepare the player with the source
        player?.prepare(mediaSource)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the player when it's no longer needed
        player?.release()
    }

    private fun buildMediaSource(): MediaSource {
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.big_buck_bunny_720p_30mb)
        val mediaItem = MediaItem.fromUri(uri)
        val dataSourceFactory = DefaultDataSourceFactory(this, null, null)
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mediaItem)
    }
}