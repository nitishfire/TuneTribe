package com.example.musicplayer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.widget.Toast

@Suppress("UNUSED_EXPRESSION")
class ApplicationClass: Application() {

    companion object{
        const val CHANNEL_ID = "channel1"
        const val PLAY = "Play"
        const val NEXT = "Next"
        const val PREVIOUS = "Prev"
        const val EXIT = "Exit"
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, "Now Playing Song", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.description = "This is a important channel for showing song!!"
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}