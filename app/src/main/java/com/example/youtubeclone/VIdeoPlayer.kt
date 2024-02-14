package com.example.youtubeclone

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.SeekBar
import android.widget.Toast
import android.widget.VideoView
import com.google.android.exoplayer2.*
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
import com.google.firebase.firestore.EventListener
import com.google.android.exoplayer2.Player.*



class VIdeoPlayer : AppCompatActivity() {
    private var player: SimpleExoPlayer? = null
    private lateinit var playerView: PlayerView
    private lateinit var seekBar :  SeekBar
    private lateinit var seekBarHandler : Handler
    private var playbackSpeed = 1f
    private lateinit var fullScreenButton : ImageView
    private var isFullScreenMode = false
    private var originalLayoutParams: LinearLayout.LayoutParams? = null
    private lateinit var leftArea : LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        playerView = findViewById(R.id.playerView)
        seekBar = findViewById(R.id.seekBar)
        fullScreenButton = findViewById(R.id.exo_fullscreen)
        leftArea = findViewById(R.id.leftArea)

        // Initialize the player
        player = SimpleExoPlayer.Builder(this)
            .setTrackSelector(DefaultTrackSelector(this))
            .build()

        // Attach the player to the view
        playerView.player = player

        // Prepare the media source
        val mediaSource = buildMediaSource()

        // Prepare the player with the source
        player?.prepare(mediaSource)

        var video_duration = player?.duration ?: 0

//        leftArea.setOnClickListener(object : DoubleClickListener() {
//            override fun onDoubleClick(v: View?) {
//                Toast.makeText(applicationContext,"Double Click", Toast.LENGTH_SHORT).show()
//            } )


        player!!.addListener(object : Player.Listener { // player listener

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) { // check player play back state
                    Player.STATE_READY -> {
                        Log.d("video_duration "," "+ (player?.duration ?: 0))
                        seekBar.max=(player?.duration ?: 0).toInt()
                    }
                    Player.STATE_ENDED -> {
                        //your logic
                    }
                    Player.STATE_BUFFERING ->{
                        //your logic
                    }
                    Player.STATE_IDLE -> {
                        //your logic
                    }
                    else -> {
                        playerView.hideController()
                    }
                }
            }
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {

                    player?.seekTo(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // User started dragging the seek bar
                // You can perform any necessary actions here
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // User stopped dragging the seek bar
                // You can perform any necessary actions here
            }
        })

        seekBarHandler = Handler()
        // Start updating SeekBar progress periodically
        seekBarHandler.postDelayed(updateProgressTask, 1000) // Start immediately, with a delay of 1000 milliseconds

        setPlaybackSpeed(2.5f)

        fullScreenButton.setOnClickListener {
            if (isFullScreenMode) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                showSystemUI()
            } else {
                hideSystemUI()
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            isFullScreenMode = !isFullScreenMode // Toggle full-screen mode flag
        }

    }
    abstract class DoubleClickListener : View.OnClickListener {
        var lastClickTime: Long = 0
        override fun onClick(v: View?) {
            val clickTime = System.currentTimeMillis()
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                onDoubleClick(v)
            }
            lastClickTime = clickTime
            }

            abstract fun onDoubleClick(v: View?)

            companion object {
                private const val DOUBLE_CLICK_TIME_DELTA: Long = 300 //milliseconds
            //
            }
    }
                // Function to hide system UI (status bar)
    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                )
    }

    // Function to show system UI (status bar)
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                )
    }



    // Define the task to update SeekBar progress
    val updateProgressTask = object : Runnable {
        override fun run() {
            // Update SeekBar progress with the current position of the player
            seekBar.progress = player?.currentPosition?.toInt() ?: 0
            // Schedule the next update after a delay
            seekBarHandler.postDelayed(this, 1000) // Delay in milliseconds (e.g., 1000 for 1 second)
        }
    }

    // Example of changing playback speed
    fun setPlaybackSpeed(speed: Float) {
        // Update playback speed
        playbackSpeed = speed
        // Set the playback speed for the player
        player?.setPlaybackParameters(PlaybackParameters(playbackSpeed))
    }

    fun playNext(){

    }

    fun playPrevious(){

    }

    fun onDoubleTapRight(){

    }
    fun onDoubleTapLeft(){

    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the player when it's no longer needed
        player?.release()
        seekBarHandler.removeCallbacks(updateProgressTask)
    }

    private fun buildMediaSource(): MediaSource {
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.big_buck_bunny_720p_30mb)
        val mediaItem = MediaItem.fromUri(uri)

        val dataSourceFactory = DefaultDataSourceFactory(this, null, null)
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mediaItem)
    }
}
