package com.example.musicplayer

/*import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.appsearch.AppSearchSession
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.session.MediaSession
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.request.target.NotificationTarget
import com.example.musicplayer.model.formatDuration
import com.example.musicplayer.model.getImageArt
import com.example.musicplayer.model.setSongPosition


class MusicService : Service(){
    private var myBinder = MyBinder()
    private lateinit var mediaSession: MediaSessionCompat
    var mediaPlayer: MediaPlayer? = null
    private lateinit var runnable: Runnable

    override fun onBind(intent: Intent?): IBinder? {
        // This makes a new session when you play 2 songs at the same time on different devices
        mediaSession = MediaSessionCompat(baseContext, "TuneTribe")
        return myBinder
    }

    // Inner class helps to return the main object
    inner class MyBinder : Binder() {
        fun currentService(): MusicService {
            return this@MusicService
        }
    }


    @SuppressLint("ForegroundServiceType", "NewApi", "SuspiciousIndentation")
    fun showNotification(playPauseBtn: Int, playbackSpeed: Float) {

        val prevIntent = Intent(
            baseContext,
            NotificationReceiver::class.java
        ).setAction(ApplicationClass.PREVIOUS)
        val prevPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            prevIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val playIntent =
            Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.PLAY)
        val playPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            playIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val exitIntent =
            Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.EXIT)
        val exitPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            exitIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val nextIntent =
            Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.NEXT)
        val nextPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            nextIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val imgArt = getImageArt(PlayerActivity.musicListPA[PlayerActivity.songPositio].path)
        val image = if (imgArt != null) {
            BitmapFactory.decodeByteArray(imgArt, 0, imgArt.size)
        } else {
            BitmapFactory.decodeResource(resources, R.drawable.music_app_logo_black_trans)
        }

        val notification = NotificationCompat.Builder(baseContext, ApplicationClass.CHANNEL_ID)
            .setContentTitle(PlayerActivity.musicListPA[PlayerActivity.songPositio].title)
            .setContentText(PlayerActivity.musicListPA[PlayerActivity.songPositio].album)
            .setSmallIcon(R.drawable.music_icon)
            .setLargeIcon(image)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.back_arrow, "Prev", prevPendingIntent)
            .addAction(playPauseBtn, "Play", playPendingIntent)
            .addAction(R.drawable.ford_arrow, "Next", nextPendingIntent)
            .addAction(R.drawable.log_out_logo, "Exit", exitPendingIntent)
            .build()

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                mediaSession.setMetadata(MediaMetadataCompat.Builder()
                    .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, mediaPlayer!!.duration.toLong())
                    .build()
                )
                val playBackState = PlaybackStateCompat.Builder()
                    .setState(PlaybackStateCompat.STATE_PLAYING, mediaPlayer!!.currentPosition.toLong(), playbackSpeed)
                    .setActions(PlaybackStateCompat.ACTION_SEEK_TO)
                    .build()

            }

        // Start the service as a foreground service
        startForeground(13, notification)
        //,  ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
    }


    fun mediaPlayerProp() {
            // Release existing MediaPlayer if it's not null
            PlayerActivity.musicService?.mediaPlayer?.release()

            // Create a new MediaPlayer instance
            PlayerActivity.musicService?.mediaPlayer = MediaPlayer()

            // Set the data source for the selected song
            PlayerActivity.musicService?.mediaPlayer?.setDataSource(PlayerActivity.musicListPA[PlayerActivity.songPositio].path)

            // Prepare and start the MediaPlayer
            PlayerActivity.musicService?.mediaPlayer?.prepare()
            PlayerActivity.nowPlayingId = PlayerActivity.musicListPA[PlayerActivity.songPositio].id

            //PlayerActivity.musicService!!.mediaPlayer!!.setOnCompletionListener(this)
            PlayerActivity.binding.floatingActionButton2.setImageResource(R.drawable.pause_icon)

            PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon, 0F)

            //SeekBar
            PlayerActivity.binding.textView16.text =
                formatDuration(PlayerActivity.musicService?.mediaPlayer!!.currentPosition.toLong())
            PlayerActivity.binding.textView17.text =
                formatDuration(PlayerActivity.musicService?.mediaPlayer!!.duration.toLong())
            PlayerActivity.binding.seekBarPA.progress = 0
            PlayerActivity.binding.seekBarPA.max =
                PlayerActivity.musicService?.mediaPlayer!!.duration
    }


    fun seekBarSetup() {
        runnable = Runnable {
            PlayerActivity.binding.textView16.text =
                formatDuration(mediaPlayer!!.currentPosition.toLong())
            PlayerActivity.binding.seekBarPA.progress = mediaPlayer!!.currentPosition
            Handler(Looper.getMainLooper()).postDelayed(runnable, 200)
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the MediaPlayer when the service is destroyed
        mediaPlayer?.release()
    }


    /*private val myInterface: MediaPlayerInterface = PlayerActivity()
    override fun onCompletion(mp: MediaPlayer?) {
        setSongPosition(increment = true)
        myInterface.setLayout()
        myInterface.createMediaPlayer()
    }*/

}*/

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import com.example.musicplayer.model.formatDuration
import com.example.musicplayer.model.getImageArt


class MusicService : Service(){
    private var myBinder = MyBinder()
    private lateinit var mediaSession: MediaSessionCompat
    var mediaPlayer: MediaPlayer? = null
    private lateinit var runnable: Runnable

    override fun onBind(intent: Intent?): IBinder? {
        // This makes a new session when you play 2 songs at the same time on different devices
        mediaSession = MediaSessionCompat(baseContext, "TuneTribe")
        return myBinder
    }

    // Inner class helps to return the main object
    inner class MyBinder : Binder() {
        fun currentService(): MusicService {
            return this@MusicService
        }
    }


    @SuppressLint("ForegroundServiceType", "NewApi", "SuspiciousIndentation")
    fun showNotification(playPauseBtn: Int, playbackSpeed: Float) {

        val prevIntent = Intent(
            baseContext,
            NotificationReceiver::class.java
        ).setAction(ApplicationClass.PREVIOUS)
        val prevPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            prevIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val playIntent =
            Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.PLAY)
        val playPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            playIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val exitIntent =
            Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.EXIT)
        val exitPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            exitIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val nextIntent =
            Intent(baseContext, NotificationReceiver::class.java).setAction(ApplicationClass.NEXT)
        val nextPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            nextIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val imgArt = getImageArt(PlayerActivity.musicListPA[PlayerActivity.songPositio].url!!)
        val image = if (imgArt != null) {
            BitmapFactory.decodeByteArray(imgArt, 0, imgArt.size)
        } else {
            BitmapFactory.decodeResource(resources, R.drawable.music_app_logo_black_trans)
        }

        val notification = NotificationCompat.Builder(baseContext, ApplicationClass.CHANNEL_ID)
            .setContentTitle(PlayerActivity.musicListPA[PlayerActivity.songPositio].name)
            .setContentText(PlayerActivity.musicListPA[PlayerActivity.songPositio].artist)
            .setSmallIcon(R.drawable.music_icon)
            .setLargeIcon(image)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.back_arrow, "Prev", prevPendingIntent)
            .addAction(playPauseBtn, "Play", playPendingIntent)
            .addAction(R.drawable.ford_arrow, "Next", nextPendingIntent)
            .addAction(R.drawable.log_out_logo, "Exit", exitPendingIntent)
            .build()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            mediaSession.setMetadata(MediaMetadataCompat.Builder()
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, mediaPlayer!!.duration.toLong())
                .build()
            )
            val playBackState = PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PLAYING, mediaPlayer!!.currentPosition.toLong(), playbackSpeed)
                .setActions(PlaybackStateCompat.ACTION_SEEK_TO)
                .build()

        }

        // Start the service as a foreground service
        startForeground(13, notification)
        //,  ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
    }


    fun mediaPlayerProp() {
        // Release existing MediaPlayer if it's not null
        PlayerActivity.musicService?.mediaPlayer?.release()

        // Create a new MediaPlayer instance
        PlayerActivity.musicService?.mediaPlayer = MediaPlayer()

        // Set the data source for the selected song
        PlayerActivity.musicService?.mediaPlayer?.setDataSource(PlayerActivity.musicListPA[PlayerActivity.songPositio].url)

        // Prepare and start the MediaPlayer
        PlayerActivity.musicService?.mediaPlayer?.prepare()
        PlayerActivity.nowPlayingId = PlayerActivity.musicListPA[PlayerActivity.songPositio].name!!

        //PlayerActivity.musicService!!.mediaPlayer!!.setOnCompletionListener(this)
        PlayerActivity.binding.floatingActionButton2.setImageResource(R.drawable.pause_icon)

        PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon, 0F)

        //SeekBar
        PlayerActivity.binding.textView16.text =
            formatDuration(PlayerActivity.musicService?.mediaPlayer!!.currentPosition.toLong())
        PlayerActivity.binding.textView17.text =
            formatDuration(PlayerActivity.musicService?.mediaPlayer!!.duration.toLong())
        PlayerActivity.binding.seekBarPA.progress = 0
        PlayerActivity.binding.seekBarPA.max =
            PlayerActivity.musicService?.mediaPlayer!!.duration
    }


    fun seekBarSetup() {
        runnable = Runnable {
            PlayerActivity.binding.textView16.text =
                formatDuration(mediaPlayer!!.currentPosition.toLong())
            PlayerActivity.binding.seekBarPA.progress = mediaPlayer!!.currentPosition
            Handler(Looper.getMainLooper()).postDelayed(runnable, 200)
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release the MediaPlayer when the service is destroyed
        mediaPlayer?.release()
    }


    /*private val myInterface: MediaPlayerInterface = PlayerActivity()
    override fun onCompletion(mp: MediaPlayer?) {
        setSongPosition(increment = true)
        myInterface.setLayout()
        myInterface.createMediaPlayer()
    }*/

}

