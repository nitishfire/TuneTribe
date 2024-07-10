package com.example.musicplayer

/*import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.example.musicplayer.fragment.NowPlaying
import com.example.musicplayer.model.likedChecker
import com.example.musicplayer.model.setSongPosition
import kotlin.system.exitProcess

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            ApplicationClass.PREVIOUS -> if(PlayerActivity.musicListPA.size > 1) prevNextSong(increment = false, context = context!!)
            ApplicationClass.NEXT -> if(PlayerActivity.musicListPA.size > 1) prevNextSong(increment = true, context = context!!)
            ApplicationClass.PLAY -> if(PlayerActivity.isPlaying) pauseMusic() else playMusic()
            ApplicationClass.EXIT -> {
                PlayerActivity.musicService?.stopForeground(true)
                PlayerActivity.musicService?.mediaPlayer!!.release()
                PlayerActivity.musicService=null
                exitProcess(1)
            }
        }
    }

    private fun playMusic(){
        PlayerActivity.isPlaying=true
        PlayerActivity.musicService!!.mediaPlayer!!.start()
        PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon, 0F)
        PlayerActivity.binding.floatingActionButton2.setImageResource(R.drawable.pause_icon)
        NowPlaying.binding.NPPlay.setImageResource(R.drawable.pause_icon)
    }

    private fun pauseMusic(){
        PlayerActivity.isPlaying=false
        PlayerActivity.musicService!!.mediaPlayer!!.pause()
        PlayerActivity.musicService!!.showNotification(R.drawable.play_arrow, 0F)
        PlayerActivity.binding.floatingActionButton2.setImageResource(R.drawable.play_arrow)
        NowPlaying.binding.NPPlay.setImageResource(R.drawable.play_arrow)
    }

    private fun prevNextSong(increment: Boolean, context: Context){
        setSongPosition(increment = increment)
        PlayerActivity.musicService!!.mediaPlayerProp()
        Glide.with(context)
            .load(PlayerActivity.musicListPA[PlayerActivity.songPositio].image)
            .placeholder(R.drawable.logo_white)
            .error(R.drawable.ic_launcher_foreground)
            .into(PlayerActivity.binding.imageView3)
        PlayerActivity.binding.textView12.text = PlayerActivity.musicListPA[PlayerActivity.songPositio].title
        Glide.with(context)
            .load(PlayerActivity.musicListPA[PlayerActivity.songPositio].image)
            .placeholder(R.drawable.logo_white)
            .error(R.drawable.ic_launcher_foreground)
            .into(NowPlaying.binding.NPimage)
        NowPlaying.binding.textView9.text = PlayerActivity.musicListPA[PlayerActivity.songPositio].title
        PlayerActivity.binding.textView13.text = PlayerActivity.musicListPA[PlayerActivity.songPositio].album
        playMusic()
        PlayerActivity.lIndex= likedChecker(PlayerActivity.musicListPA[PlayerActivity.songPositio].id)
        if(PlayerActivity.isLiked){
            PlayerActivity.binding.likeBtn.setImageResource(R.drawable.full_liked_btn)
        }else{
            PlayerActivity.binding.likeBtn.setImageResource(R.drawable.like_btn)
        }
    }
}*/

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.example.musicplayer.fragment.NowPlaying
import com.example.musicplayer.model.likedChecker
import com.example.musicplayer.model.setSongPosition
import kotlin.system.exitProcess

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            ApplicationClass.PREVIOUS -> if(PlayerActivity.musicListPA.size > 1) prevNextSong(increment = false, context = context!!)
            ApplicationClass.NEXT -> if(PlayerActivity.musicListPA.size > 1) prevNextSong(increment = true, context = context!!)
            ApplicationClass.PLAY -> if(PlayerActivity.isPlaying) pauseMusic() else playMusic()
            ApplicationClass.EXIT -> {
                PlayerActivity.musicService?.stopForeground(true)
                PlayerActivity.musicService?.mediaPlayer!!.release()
                PlayerActivity.musicService=null
                exitProcess(1)
            }
        }
    }

    private fun playMusic(){
        PlayerActivity.isPlaying=true
        PlayerActivity.musicService!!.mediaPlayer!!.start()
        PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon, 0F)
        PlayerActivity.binding.floatingActionButton2.setImageResource(R.drawable.pause_icon)
        NowPlaying.binding.NPPlay.setImageResource(R.drawable.pause_icon)
    }

    private fun pauseMusic(){
        PlayerActivity.isPlaying=false
        PlayerActivity.musicService!!.mediaPlayer!!.pause()
        PlayerActivity.musicService!!.showNotification(R.drawable.play_arrow, 0F)
        PlayerActivity.binding.floatingActionButton2.setImageResource(R.drawable.play_arrow)
        NowPlaying.binding.NPPlay.setImageResource(R.drawable.play_arrow)
    }

    private fun prevNextSong(increment: Boolean, context: Context){
        setSongPosition(increment = increment)
        PlayerActivity.musicService!!.mediaPlayerProp()
        Glide.with(context)
            .load(PlayerActivity.musicListPA[PlayerActivity.songPositio].image)
            .placeholder(R.drawable.logo_white)
            .error(R.drawable.ic_launcher_foreground)
            .into(PlayerActivity.binding.imageView3)
        PlayerActivity.binding.textView12.text = PlayerActivity.musicListPA[PlayerActivity.songPositio].name
        Glide.with(context)
            .load(PlayerActivity.musicListPA[PlayerActivity.songPositio].image)
            .placeholder(R.drawable.logo_white)
            .error(R.drawable.ic_launcher_foreground)
            .into(NowPlaying.binding.NPimage)
        NowPlaying.binding.textView9.text = PlayerActivity.musicListPA[PlayerActivity.songPositio].name
        PlayerActivity.binding.textView13.text = PlayerActivity.musicListPA[PlayerActivity.songPositio].artist
        playMusic()
        PlayerActivity.lIndex= likedChecker(PlayerActivity.musicListPA[PlayerActivity.songPositio].name)
        if(PlayerActivity.isLiked){
            PlayerActivity.binding.likeBtn.setImageResource(R.drawable.full_liked_btn)
        }else{
            PlayerActivity.binding.likeBtn.setImageResource(R.drawable.like_btn)
        }
    }
}