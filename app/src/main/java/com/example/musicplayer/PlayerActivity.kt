package com.example.musicplayer


//import android.annotation.SuppressLint
//import android.content.ComponentName
//import android.content.Intent
//import android.content.ServiceConnection
//import android.media.MediaPlayer
//import android.net.Uri
//import android.os.Bundle
//import android.os.IBinder
//import android.view.WindowManager
//import android.widget.SeekBar
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import com.bumptech.glide.Glide
//import com.example.musicplayer.databinding.ActivityPlayerBinding
//import com.example.musicplayer.fragment.SearchFragment
//import com.example.musicplayer.model.Music
//import com.example.musicplayer.model.flag
//import com.example.musicplayer.model.formatDuration
//import com.example.musicplayer.model.likedChecker
//import com.example.musicplayer.model.setSongPosition
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//
//
//class PlayerActivity : AppCompatActivity(), ServiceConnection, MediaPlayer.OnCompletionListener, MediaPlayerInterface {
//    // Add these fields to your PlayerActivity
//    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var databaseReference: DatabaseReference
//
//    companion object{
//        lateinit var musicListPA: ArrayList<Music>
//        //It Shows Current Song Posiiton
//        var songPositio: Int = 0
//        var isPlaying:Boolean = false
//        var nowPlayingId: String = ""
//        //var mediaPlayer: MediaPlayer? = null
//        var musicService: MusicService? = null
//        @SuppressLint("StaticFieldLeak")
//        lateinit var binding: ActivityPlayerBinding
//
//        //Repeat Button
//        var repeat: Boolean =false
//
//        //Like Button
//        var isLiked: Boolean = false
//        var lIndex: Int = -1
//
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding=ActivityPlayerBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.textView12.isSelected=true
//        binding.textView13.isSelected=true
//        // Get the selected song position from the intent
//        songPositio = intent.getIntExtra("index", 0)
//        // Check the class name in the intent to determine the source of the song list
//        createMediaPlayer()
//        //Layout Function is called here
//        setLayout()
//
//
//        //Play/Pause
//        binding.floatingActionButton2.setOnClickListener{
//            if(isPlaying) pauseMusic()
//            else playingMusic()
//        }
//
//        //Share Button Function Calling
//        binding.imageButton10.setOnClickListener {
//            val shareIntent = Intent()
//            shareIntent.action = Intent.ACTION_SEND
//            shareIntent.type = "audio/*"
//            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(musicListPA[songPositio].path))
//            startActivity(Intent.createChooser(shareIntent, "Sharing Music File!!"))
//        }
//
//        binding.floatingActionButton3.setOnClickListener{
//            prevNextSong(increment = false)
//        }
//        binding.floatingActionButton.setOnClickListener{
//            prevNextSong(increment = true)
//        }
//
//
//        //SeekBar
//         binding.seekBarPA.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
//             override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                 if(fromUser) musicService?.mediaPlayer?.seekTo(progress)
//             }
//
//             override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
//
//             override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
//         })
//
//
//        //Back Button
//        binding.imageButton8.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            if (!isPlaying) {
//                playingMusic()
//            }
//            moveTaskToBack(true)
//            startActivity(intent)
//        }
//
//        //Like Button
//
//        // Inside your onCreate() method, initialize Firebase Authentication and Database
//        firebaseAuth = FirebaseAuth.getInstance()
//        databaseReference = FirebaseDatabase.getInstance().reference.child("user").child(firebaseAuth.currentUser?.uid ?: "")
//
//        binding.likeBtn.setOnClickListener{
//            if(isLiked){
//                isLiked = false
//                binding.likeBtn.setImageResource(R.drawable.like_btn)
//                if (lIndex != -1) {
//                    Liked_Songs.likedSongs.removeAt(lIndex)
//                }
//            } else {
//                isLiked = true
//                binding.likeBtn.setImageResource(R.drawable.full_liked_btn)
//                Liked_Songs.likedSongs.add(musicListPA[songPositio])
//            }
//        }
//
//
//        //Repeat Button
//        binding.imageButton9.setOnClickListener{
//            if(!repeat){
//                repeat = true
//                binding.imageButton9.setColorFilter(ContextCompat.getColor(this, R.color.button_blue_color_welcome_page))
//            }else{
//                repeat = false
//                binding.imageButton9.setColorFilter(ContextCompat.getColor(this, R.color.white))
//            }
//        }
//
//
//
//        supportActionBar?.hide()
//        this.window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )
//    }
//
//    @SuppressLint("CommitPrefEdits")
//    override fun onDestroy() {
//            super.onDestroy()
//            if(musicListPA[songPositio].id == "Unknown" && !isPlaying) finish()
//    }
//
//
//    @SuppressLint("CheckResult")
//    override fun setLayout() {
//        if (musicListPA.isNotEmpty()) {
//            lIndex= likedChecker(musicListPA[songPositio].id)
//            Glide.with(this)
//                .load(musicListPA[songPositio].image)
//                .placeholder(R.drawable.logo_white)
//                .error(R.drawable.ic_launcher_foreground)
//                .into(binding.imageView3)
//
//            binding.textView12.text = musicListPA[songPositio].title
//            binding.textView13.text = musicListPA[songPositio].album
//
//            if (repeat) {
//                binding.imageButton9.setColorFilter(ContextCompat.getColor(this, R.color.button_blue_color_welcome_page))
//            }
//            if(isLiked){
//                binding.likeBtn.setImageResource(R.drawable.full_liked_btn)
//            }else{
//                binding.likeBtn.setImageResource(R.drawable.like_btn )
//            }
//        }
//    }
//
//
//    override fun createMediaPlayer(){
//        try {
//            when (intent.getStringExtra("class")) {
//                "PlaylistDetailsAdapter" -> {
//                    val intent = Intent(this, MusicService::class.java)
//                    startService(intent)
//                    bindService(intent, this , BIND_AUTO_CREATE)
//                    musicListPA = ArrayList()
//                    musicListPA.addAll(playlist_main.musicPlaylist.ref[PlaylistDetails.currentPlaylistPos].playlist)
//                    setLayout()
//                    if (isPlaying) {
//                        pauseMusic()
//                        mediaPlayerProp()
//                    } else {
//                        playingMusic()
//                        pauseMusic()
//                    }
//                }
//                "LikedAdapter" ->{
//                    val intent = Intent(this, MusicService::class.java)
//                    startService(intent)
//                    bindService(intent, this , BIND_AUTO_CREATE)
//                    musicListPA = ArrayList()
//                    musicListPA.addAll(Liked_Songs.likedSongs)
//                    setLayout()
//                    if (isPlaying) {
//                        pauseMusic()
//                        mediaPlayerProp()
//                    } else {
//                        playingMusic()
//                        pauseMusic()
//                    }
//                }
//                "NowPlaying" -> {
//                    binding.textView16.text = formatDuration(musicService!!.mediaPlayer!!.currentPosition.toLong())
//                    binding.textView17.text = formatDuration(musicService!!.mediaPlayer!!.duration.toLong())
//                    binding.seekBarPA.progress = musicService!!.mediaPlayer!!.currentPosition
//                    binding.seekBarPA.max = musicService!!.mediaPlayer!!.duration
//                    if(isPlaying) binding.floatingActionButton2.setImageResource(R.drawable.pause_icon)
//                    else binding.floatingActionButton2.setImageResource(R.drawable.play_arrow)
//                    setLayout()
//                }
//                "MusicAdapterSearch" -> {
//                    //Music Service
//                    val intent = Intent(this, MusicService::class.java)
//                    startService(intent)
//                    bindService(intent, this , BIND_AUTO_CREATE)
//                    musicListPA = ArrayList()
//                    musicListPA.addAll(SearchFragment.musicListSearch)
//                    setLayout()
//                    if (isPlaying) {
//                        pauseMusic()
//                        mediaPlayerProp()
//                    } else {
//                        playingMusic()
//                        pauseMusic()
//                    }
//                }
//                "MusicSearchMain" -> {
//                    //Music Service
//                    val intent = Intent(this, MusicService::class.java)
//                    startService(intent)
//                    bindService(intent, this , BIND_AUTO_CREATE)
//                    musicListPA = ArrayList()
//                    musicListPA.addAll(SearchFragment.musicListMA1)
//                    setLayout()
//                    if (isPlaying) {
//                        pauseMusic()
//                        mediaPlayerProp()
//                    } else {
//                        playingMusic()
//                        pauseMusic()
//                    }
//                }
//                "PlaylistDetailsShuffle" -> {
//                    val intent = Intent(this, MusicService::class.java)
//                    startService(intent)
//                    bindService(intent, this , BIND_AUTO_CREATE)
//                    musicListPA = ArrayList()
//                    musicListPA.addAll(playlist_main.musicPlaylist.ref[PlaylistDetails.currentPlaylistPos].playlist)
//                    musicListPA.shuffle()
//                    setLayout()
//
//                    if (isPlaying) {
//                        pauseMusic()
//                        mediaPlayerProp()
//                    } else {
//                        playingMusic()
//                        pauseMusic()
//                    }
//                }
//                "MusicAdapter" -> {
//                    //Music Service
//                    val intent = Intent(this, MusicService::class.java)
//                    startService(intent)
//                    bindService(intent, this , BIND_AUTO_CREATE)
//                    musicListPA = ArrayList()
//                    if(flag==1){
//                        musicListPA.addAll(lofi_album_layout.musicListMA)
//                    }
//
//                    if (flag==2){
//                        //One Direction
//                        musicListPA.addAll(oneD_album_layout.musicListMA)
//                      }
//
//                    if (flag==3){
//                        //Coffee And Jazz
//                        musicListPA.addAll(coffeeZ_album_layout.musicListMA)
//                      }
//
//                    if (flag==4){
//                        //Ed Sheeran
//                        musicListPA.addAll(edsheeran_album_layout.musicListMA)
//                    }
//
//                    if(flag==5){
//                        //Newly Released
//                        musicListPA.addAll(released_album_layout.musicListMA)
//                    }
//
//                    if(flag==6){
//                        //Imagine Dragons
//                        musicListPA.addAll(Idragon_album_layout.musicListMA)
//                    }
//
//                    if(flag==7){
//                        musicListPA.addAll(charliePuth_album_activity.musicListMA)
//                    }
//
//                    if(flag==8){
//                        musicListPA.addAll(weekend_album_layout.musicListMA)
//                    }
//
//                    if(flag==9){
//                        musicListPA.addAll(shawnM_album_layout.musicListMA)
//                    }
//                    setLayout()
//                    if (isPlaying) {
//                        pauseMusic()
//                        mediaPlayerProp()
//                    } else {
//                        playingMusic()
//                        pauseMusic()
//                    }
//                }
//                "lofi_album_layout" -> {
//                    //Music Service
//                    val intent = Intent(this, MusicService::class.java)
//                    startService(intent)
//                    bindService(intent, this , BIND_AUTO_CREATE)
//                    musicListPA = ArrayList()
//                    musicListPA.addAll(lofi_album_layout.musicListMA)
//                    musicListPA.shuffle()
//                    setLayout()
//
//                    if (isPlaying) {
//                        pauseMusic()
//                        mediaPlayerProp()
//                    } else {
//                        playingMusic()
//                        pauseMusic()
//                    }
//                }
//                "oneD_album_layout" -> {
//                    //Music Service
//                    val intent = Intent(this, MusicService::class.java)
//                    startService(intent)
//                    bindService(intent, this , BIND_AUTO_CREATE)
//                    musicListPA = ArrayList()
//                    musicListPA.addAll(oneD_album_layout.musicListMA)
//                    musicListPA.shuffle()
//
//                    if (isPlaying) {
//                        pauseMusic()
//                        mediaPlayerProp()
//                    } else {
//                        playingMusic()
//                        pauseMusic()
//                    }
//                }
//                "coffeeZ_album_layout" -> {
//                    //Music Service
//                    val intent = Intent(this, MusicService::class.java)
//                    startService(intent)
//                    bindService(intent, this , BIND_AUTO_CREATE)
//                    musicListPA = ArrayList()
//                    musicListPA.addAll(coffeeZ_album_layout.musicListMA)
//                    musicListPA.shuffle()
//
//                    if (isPlaying) {
//                        pauseMusic()
//                        mediaPlayerProp()
//                    } else {
//                        playingMusic()
//                        pauseMusic()
//                    }
//                }
//                "edsheeran_album_layout" -> {
//                    //Music Service
//                    val intent = Intent(this, MusicService::class.java)
//                    startService(intent)
//                    bindService(intent, this , BIND_AUTO_CREATE)
//                    musicListPA = ArrayList()
//                    musicListPA.addAll(edsheeran_album_layout.musicListMA)
//                    musicListPA.shuffle()
//
//                    if (isPlaying) {
//                        pauseMusic()
//                        mediaPlayerProp()
//                    } else {
//                        playingMusic()
//                        pauseMusic()
//                    }
//                }
//                "released_album_layout" -> {
//                    //Music Service
//                    val intent = Intent(this, MusicService::class.java)
//                    startService(intent)
//                    bindService(intent, this , BIND_AUTO_CREATE)
//                    musicListPA = ArrayList()
//                    musicListPA.addAll(released_album_layout.musicListMA)
//                    musicListPA.shuffle()
//
//                    if (isPlaying) {
//                        pauseMusic()
//                        mediaPlayerProp()
//                    } else {
//                        playingMusic()
//                        pauseMusic()
//                    }
//                }
//                "Idragons_album_layout" -> {
//                    //Music Service
//                    val intent = Intent(this, MusicService::class.java)
//                    startService(intent)
//                    bindService(intent, this , BIND_AUTO_CREATE)
//                    musicListPA = ArrayList()
//                    musicListPA.addAll(Idragon_album_layout.musicListMA)
//                    musicListPA.shuffle()
//
//                    if (isPlaying) {
//                        pauseMusic()
//                        mediaPlayerProp()
//                    } else {
//                        playingMusic()
//                        pauseMusic()
//                    }
//                }
//                "charliePuth_album_layout" -> {
//                    //Music Service
//                    val intent = Intent(this, MusicService::class.java)
//                    startService(intent)
//                    bindService(intent, this , BIND_AUTO_CREATE)
//                    musicListPA = ArrayList()
//                    musicListPA.addAll(charliePuth_album_activity.musicListMA)
//                    musicListPA.shuffle()
//
//                    if (isPlaying) {
//                        pauseMusic()
//                        mediaPlayerProp()
//                    } else {
//                        playingMusic()
//                        pauseMusic()
//                    }
//                }
//                "weekend_album_layout" -> {
//                    //Music Service
//                    val intent = Intent(this, MusicService::class.java)
//                    startService(intent)
//                    bindService(intent, this , BIND_AUTO_CREATE)
//                    musicListPA = ArrayList()
//                    musicListPA.addAll(weekend_album_layout.musicListMA)
//                    musicListPA.shuffle()
//
//                    if (isPlaying) {
//                        pauseMusic()
//                        mediaPlayerProp()
//                    } else {
//                        playingMusic()
//                        pauseMusic()
//                    }
//                }
//                "shawnM_album_layout" -> {
//                    //Music Service
//                    val intent = Intent(this, MusicService::class.java)
//                    startService(intent)
//                    bindService(intent, this , BIND_AUTO_CREATE)
//                    musicListPA = ArrayList()
//                    musicListPA.addAll(shawnM_album_layout.musicListMA)
//                    musicListPA.shuffle()
//
//                    if (isPlaying) {
//                        pauseMusic()
//                        mediaPlayerProp()
//                    } else {
//                        playingMusic()
//                        pauseMusic()
//                    }
//                }
//            }
//
//        }catch (e:Exception){
//            return
//        }
//    }
//
//    private fun mediaPlayerProp(){
//        if (musicListPA.isNotEmpty()) {
//            // Release existing MediaPlayer if it's not null
//            musicService?.mediaPlayer?.release()
//
//            // Create a new MediaPlayer instance
//            musicService?.mediaPlayer = MediaPlayer()
//
//            // Set the data source for the selected song
//            musicService?.mediaPlayer?.setDataSource(musicListPA[songPositio].path)
//
//            // Prepare and start the MediaPlayer
//            musicService?.mediaPlayer?.prepare()
//
//            musicService?.mediaPlayer?.start()
//
//            musicService!!.mediaPlayer!!.setOnCompletionListener(this)
//            isPlaying = true
//            binding.floatingActionButton2.setImageResource(R.drawable.pause_icon)
//
//            musicService!!.showNotification(R.drawable.pause_icon, 0F)
//            nowPlayingId = musicListPA[songPositio].id
//            playingMusic()
//            //SeekBar
//            binding.textView16.text = formatDuration(musicService?.mediaPlayer!!.currentPosition.toLong())
//            binding.textView17.text = formatDuration(musicService?.mediaPlayer!!.duration.toLong())
//            binding.seekBarPA.progress = 0
//            binding.seekBarPA.max = musicService?.mediaPlayer!!.duration
//            setLayout()
//        } else {
//            // Handle the case where musicListPA is empty (e.g., show an error message)
//            Toast.makeText(this, "No music available", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//
//    private fun playingMusic(){
//        binding.floatingActionButton2.setImageResource(R.drawable.pause_icon)
//        musicService?.showNotification(R.drawable.pause_icon, 0F)
//        isPlaying=true
//        musicService?.mediaPlayer?.start()
//    }
//
//    private fun pauseMusic(){
//        binding.floatingActionButton2.setImageResource(R.drawable.play_arrow)
//        musicService?.showNotification(R.drawable.play_arrow, 0F)
//        isPlaying=false
//        musicService?.mediaPlayer?.pause()
//    }
//
//    private fun prevNextSong(increment: Boolean){
//        if(increment)
//        {
//            setSongPosition(increment = true)
//            setLayout()
//            createMediaPlayer()
//        }else{
//            setSongPosition(increment = false)
//            setLayout()
//            createMediaPlayer()
//        }
//    }
//
//
//
//    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//        if(musicService == null) {
//            val binder = service as MusicService.MyBinder
//            musicService = binder.currentService()
//        }
//        mediaPlayerProp()
//        musicService?.seekBarSetup()
//    }
//
//    override fun onServiceDisconnected(name: ComponentName?) {
//        musicService = null
//    }
//
//
//    @SuppressLint("MissingSuperCall")
//    override fun onBackPressed() {
//        val intent = Intent(this, MainActivity::class.java)
//        if (!isPlaying) {
//            playingMusic()
//        }
//        moveTaskToBack(true)
//        startActivity(intent)
//    }
//
//    override fun onCompletion(mp: MediaPlayer?) {
//        setSongPosition(increment = true)
//        createMediaPlayer()
//        setLayout()
//    }
//}
//
//interface MediaPlayerInterface {
//    fun createMediaPlayer()
//    fun setLayout()
//}


import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.musicplayer.databinding.ActivityPlayerBinding
import com.example.musicplayer.fragment.SearchFragment
import com.example.musicplayer.model.Music
import com.example.musicplayer.model.flag
import com.example.musicplayer.model.formatDuration
import com.example.musicplayer.model.likedChecker
import com.example.musicplayer.model.setSongPosition
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class PlayerActivity : AppCompatActivity(), ServiceConnection, MediaPlayer.OnCompletionListener, MediaPlayerInterface {
    // Add these fields to your PlayerActivity
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    companion object{
        lateinit var musicListPA: ArrayList<Music>
        //It Shows Current Song Posiiton
        var songPositio: Int = 0
        var isPlaying:Boolean = false
        var nowPlayingId: String = ""
        //var mediaPlayer: MediaPlayer? = null
        var musicService: MusicService? = null
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityPlayerBinding

        //Repeat Button
        var repeat: Boolean =false

        //Like Button
        var isLiked: Boolean = false
        var lIndex: Int = -1

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView12.isSelected=true
        binding.textView13.isSelected=true
        // Get the selected song position from the intent
        songPositio = intent.getIntExtra("index", 0)
        // Check the class name in the intent to determine the source of the song list
        createMediaPlayer()
        //Layout Function is called here
        setLayout()


        //Play/Pause
        binding.floatingActionButton2.setOnClickListener{
            if(isPlaying) pauseMusic()
            else playingMusic()
        }

        //Share Button Function Calling
        binding.imageButton10.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "audio/*"
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(musicListPA[songPositio].url))
            startActivity(Intent.createChooser(shareIntent, "Sharing Music File!!"))
        }

        binding.floatingActionButton3.setOnClickListener{
            prevNextSong(increment = false)
        }
        binding.floatingActionButton.setOnClickListener{
            prevNextSong(increment = true)
        }


        //SeekBar
        binding.seekBarPA.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) musicService?.mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })


        //Back Button
        binding.imageButton8.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            if (!isPlaying) {
                playingMusic()
            }
            moveTaskToBack(true)
            startActivity(intent)
        }

        //Like Button

        // Inside your onCreate() method, initialize Firebase Authentication and Database
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("user").child(firebaseAuth.currentUser?.uid ?: "")

        binding.likeBtn.setOnClickListener {
            LikeDB()
        }


        //Repeat Button
        binding.imageButton9.setOnClickListener{
            if(!repeat){
                repeat = true
                binding.imageButton9.setColorFilter(ContextCompat.getColor(this, R.color.button_blue_color_welcome_page))
            }else{
                repeat = false
                binding.imageButton9.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }
        }



        supportActionBar?.hide()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    @SuppressLint("CommitPrefEdits")
    override fun onDestroy() {
        super.onDestroy()
        if(musicListPA[songPositio].name == "Unknown" && !isPlaying) finish()
    }


    @SuppressLint("CheckResult")
    override fun setLayout() {
        if (musicListPA.isNotEmpty()) {
            lIndex= likedChecker(musicListPA[songPositio].name)
            Glide.with(this)
                .load(musicListPA[songPositio].image)
                .placeholder(R.drawable.logo_white)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.imageView3)

            binding.textView12.text = musicListPA[songPositio].name
            binding.textView13.text = musicListPA[songPositio].artist

            if (repeat) {
                binding.imageButton9.setColorFilter(ContextCompat.getColor(this, R.color.button_blue_color_welcome_page))
            }
            if(isLiked){
                binding.likeBtn.setImageResource(R.drawable.full_liked_btn)
            }else{
                binding.likeBtn.setImageResource(R.drawable.like_btn )
            }
        }
    }


    override fun createMediaPlayer(){
        try {
            when (intent.getStringExtra("class")) {
                "PlaylistDetailsAdapter" -> {
                    val intent = Intent(this, MusicService::class.java)
                    startService(intent)
                    bindService(intent, this , BIND_AUTO_CREATE)
                    musicListPA = ArrayList()
                    musicListPA.addAll(playlist_main.musicPlaylist.ref[PlaylistDetails.currentPlaylistPos].playlist)
                    setLayout()
                    if (isPlaying) {
                        pauseMusic()
                        mediaPlayerProp()
                    } else {
                        playingMusic()
                        pauseMusic()
                    }
                }
                "LikedAdapter" ->{
                    val intent = Intent(this, MusicService::class.java)
                    startService(intent)
                    bindService(intent, this , BIND_AUTO_CREATE)
                    musicListPA = ArrayList()
                    musicListPA.addAll(Liked_Songs.likedSongs)
                    setLayout()
                    if (isPlaying) {
                        pauseMusic()
                        mediaPlayerProp()
                    } else {
                        playingMusic()
                        pauseMusic()
                    }
                }
                "NowPlaying" -> {
                    binding.textView16.text = formatDuration(musicService!!.mediaPlayer!!.currentPosition.toLong())
                    binding.textView17.text = formatDuration(musicService!!.mediaPlayer!!.duration.toLong())
                    binding.seekBarPA.progress = musicService!!.mediaPlayer!!.currentPosition
                    binding.seekBarPA.max = musicService!!.mediaPlayer!!.duration
                    if(isPlaying) binding.floatingActionButton2.setImageResource(R.drawable.pause_icon)
                    else binding.floatingActionButton2.setImageResource(R.drawable.play_arrow)
                    setLayout()
                }
                "MusicAdapterSearch" -> {
                    //Music Service
                    val intent = Intent(this, MusicService::class.java)
                    startService(intent)
                    bindService(intent, this , BIND_AUTO_CREATE)
                    musicListPA = ArrayList()
                    musicListPA.addAll(SearchFragment.musicListSearch)
                    setLayout()
                    if (isPlaying) {
                        pauseMusic()
                        mediaPlayerProp()
                    } else {
                        playingMusic()
                        pauseMusic()
                    }
                }
                "MusicSearchMain" -> {
                    //Music Service
                    val intent = Intent(this, MusicService::class.java)
                    startService(intent)
                    bindService(intent, this , BIND_AUTO_CREATE)
                    musicListPA = ArrayList()
                    musicListPA.addAll(SearchFragment.musicListMA1)
                    setLayout()
                    if (isPlaying) {
                        pauseMusic()
                        mediaPlayerProp()
                    } else {
                        playingMusic()
                        pauseMusic()
                    }
                }
                "PlaylistDetailsShuffle" -> {
                    val intent = Intent(this, MusicService::class.java)
                    startService(intent)
                    bindService(intent, this , BIND_AUTO_CREATE)
                    musicListPA = ArrayList()
                    musicListPA.addAll(playlist_main.musicPlaylist.ref[PlaylistDetails.currentPlaylistPos].playlist)
                    musicListPA.shuffle()
                    setLayout()

                    if (isPlaying) {
                        pauseMusic()
                        mediaPlayerProp()
                    } else {
                        playingMusic()
                        pauseMusic()
                    }
                }
                "MusicAdapter" -> {
                    //Music Service
                    val intent = Intent(this, MusicService::class.java)
                    startService(intent)
                    bindService(intent, this , BIND_AUTO_CREATE)
                    musicListPA = ArrayList()
                    if(flag==1){
                        musicListPA.addAll(lofi_album_layout.musicListMA)
                    }

                    if (flag==2){
                        //One Direction
                        musicListPA.addAll(oneD_album_layout.musicListMA)
                    }

                    if (flag==3){
                        //Coffee And Jazz
                        musicListPA.addAll(coffeeZ_album_layout.musicListMA)
                    }

                    if (flag==4){
                        //Ed Sheeran
                        musicListPA.addAll(edsheeran_album_layout.musicListMA)
                    }

                    if(flag==5){
                        //Newly Released
                        musicListPA.addAll(released_album_layout.musicListMA)
                    }

                    if(flag==6){
                        //Imagine Dragons
                        musicListPA.addAll(Idragon_album_layout.musicListMA)
                    }

                    if(flag==7){
                        musicListPA.addAll(charliePuth_album_activity.musicListMA)
                    }

                    if(flag==8){
                        musicListPA.addAll(weekend_album_layout.musicListMA)
                    }

                    if(flag==9){
                        musicListPA.addAll(shawnM_album_layout.musicListMA)
                    }

                    if(flag==10){
                        musicListPA.addAll(DeviceSongs.musicListMA)
                    }
                    setLayout()
                    if (isPlaying) {
                        pauseMusic()
                        mediaPlayerProp()
                    } else {
                        playingMusic()
                        pauseMusic()
                    }
                }
                "lofi_album_layout" -> {
                    //Music Service
                    val intent = Intent(this, MusicService::class.java)
                    startService(intent)
                    bindService(intent, this , BIND_AUTO_CREATE)
                    musicListPA = ArrayList()
                    musicListPA.addAll(lofi_album_layout.musicListMA)
                    musicListPA.shuffle()
                    setLayout()

                    if (isPlaying) {
                        pauseMusic()
                        mediaPlayerProp()
                    } else {
                        playingMusic()
                        pauseMusic()
                    }
                }
                "oneD_album_layout" -> {
                    //Music Service
                    val intent = Intent(this, MusicService::class.java)
                    startService(intent)
                    bindService(intent, this , BIND_AUTO_CREATE)
                    musicListPA = ArrayList()
                    musicListPA.addAll(oneD_album_layout.musicListMA)
                    musicListPA.shuffle()

                    if (isPlaying) {
                        pauseMusic()
                        mediaPlayerProp()
                    } else {
                        playingMusic()
                        pauseMusic()
                    }
                }
                "coffeeZ_album_layout" -> {
                    //Music Service
                    val intent = Intent(this, MusicService::class.java)
                    startService(intent)
                    bindService(intent, this , BIND_AUTO_CREATE)
                    musicListPA = ArrayList()
                    musicListPA.addAll(coffeeZ_album_layout.musicListMA)
                    musicListPA.shuffle()

                    if (isPlaying) {
                        pauseMusic()
                        mediaPlayerProp()
                    } else {
                        playingMusic()
                        pauseMusic()
                    }
                }
                "edsheeran_album_layout" -> {
                    //Music Service
                    val intent = Intent(this, MusicService::class.java)
                    startService(intent)
                    bindService(intent, this , BIND_AUTO_CREATE)
                    musicListPA = ArrayList()
                    musicListPA.addAll(edsheeran_album_layout.musicListMA)
                    musicListPA.shuffle()

                    if (isPlaying) {
                        pauseMusic()
                        mediaPlayerProp()
                    } else {
                        playingMusic()
                        pauseMusic()
                    }
                }
                "released_album_layout" -> {
                    //Music Service
                    val intent = Intent(this, MusicService::class.java)
                    startService(intent)
                    bindService(intent, this , BIND_AUTO_CREATE)
                    musicListPA = ArrayList()
                    musicListPA.addAll(released_album_layout.musicListMA)
                    musicListPA.shuffle()

                    if (isPlaying) {
                        pauseMusic()
                        mediaPlayerProp()
                    } else {
                        playingMusic()
                        pauseMusic()
                    }
                }
                "Idragons_album_layout" -> {
                    //Music Service
                    val intent = Intent(this, MusicService::class.java)
                    startService(intent)
                    bindService(intent, this , BIND_AUTO_CREATE)
                    musicListPA = ArrayList()
                    musicListPA.addAll(Idragon_album_layout.musicListMA)
                    musicListPA.shuffle()

                    if (isPlaying) {
                        pauseMusic()
                        mediaPlayerProp()
                    } else {
                        playingMusic()
                        pauseMusic()
                    }
                }
                "charliePuth_album_layout" -> {
                    //Music Service
                    val intent = Intent(this, MusicService::class.java)
                    startService(intent)
                    bindService(intent, this , BIND_AUTO_CREATE)
                    musicListPA = ArrayList()
                    musicListPA.addAll(charliePuth_album_activity.musicListMA)
                    musicListPA.shuffle()

                    if (isPlaying) {
                        pauseMusic()
                        mediaPlayerProp()
                    } else {
                        playingMusic()
                        pauseMusic()
                    }
                }
                "weekend_album_layout" -> {
                    //Music Service
                    val intent = Intent(this, MusicService::class.java)
                    startService(intent)
                    bindService(intent, this , BIND_AUTO_CREATE)
                    musicListPA = ArrayList()
                    musicListPA.addAll(weekend_album_layout.musicListMA)
                    musicListPA.shuffle()

                    if (isPlaying) {
                        pauseMusic()
                        mediaPlayerProp()
                    } else {
                        playingMusic()
                        pauseMusic()
                    }
                }
                "shawnM_album_layout" -> {
                    //Music Service
                    val intent = Intent(this, MusicService::class.java)
                    startService(intent)
                    bindService(intent, this , BIND_AUTO_CREATE)
                    musicListPA = ArrayList()
                    musicListPA.addAll(shawnM_album_layout.musicListMA)
                    musicListPA.shuffle()

                    if (isPlaying) {
                        pauseMusic()
                        mediaPlayerProp()
                    } else {
                        playingMusic()
                        pauseMusic()
                    }
                }
            }

        }catch (e:Exception){
            return
        }
    }

    private fun mediaPlayerProp(){
        if (musicListPA.isNotEmpty()) {
            // Release existing MediaPlayer if it's not null
            musicService?.mediaPlayer?.release()

            // Create a new MediaPlayer instance
            musicService?.mediaPlayer = MediaPlayer()

            // Set the data source for the selected song
            musicService?.mediaPlayer?.setDataSource(musicListPA[songPositio].url)

            // Prepare and start the MediaPlayer
            musicService?.mediaPlayer?.prepare()

            musicService?.mediaPlayer?.start()

            musicService!!.mediaPlayer!!.setOnCompletionListener(this)
            isPlaying = true
            binding.floatingActionButton2.setImageResource(R.drawable.pause_icon)

            musicService!!.showNotification(R.drawable.pause_icon, 0F)
            nowPlayingId = musicListPA[songPositio].name.toString()
            playingMusic()
            //SeekBar
            binding.textView16.text = formatDuration(musicService?.mediaPlayer!!.currentPosition.toLong())
            binding.textView17.text = formatDuration(musicService?.mediaPlayer!!.duration.toLong())
            binding.seekBarPA.progress = 0
            binding.seekBarPA.max = musicService?.mediaPlayer!!.duration
            setLayout()
        } else {
            // Handle the case where musicListPA is empty (e.g., show an error message)
            Toast.makeText(this, "No music available", Toast.LENGTH_SHORT).show()
        }
    }


    private fun playingMusic(){
        binding.floatingActionButton2.setImageResource(R.drawable.pause_icon)
        musicService?.showNotification(R.drawable.pause_icon, 0F)
        isPlaying=true
        musicService?.mediaPlayer?.start()
    }

    private fun pauseMusic(){
        binding.floatingActionButton2.setImageResource(R.drawable.play_arrow)
        musicService?.showNotification(R.drawable.play_arrow, 0F)
        isPlaying=false
        musicService?.mediaPlayer?.pause()
    }

    private fun prevNextSong(increment: Boolean){
        if(increment)
        {
            setSongPosition(increment = true)
            setLayout()
            createMediaPlayer()
        }else{
            setSongPosition(increment = false)
            setLayout()
            createMediaPlayer()
        }
    }



    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        if(musicService == null) {
            val binder = service as MusicService.MyBinder
            musicService = binder.currentService()
        }
        mediaPlayerProp()
        musicService?.seekBarSetup()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        if (!isPlaying) {
            playingMusic()
        }
        moveTaskToBack(true)
        startActivity(intent)
    }

    override fun onCompletion(mp: MediaPlayer?) {
        setSongPosition(increment = true)
        createMediaPlayer()
        setLayout()
    }

    private fun LikeDB(){
        val database = FirebaseDatabase.getInstance()
        val userId = retrievePaymentID()
        val userRef = database.getReference("user").child(userId!!).child("liked_songs")
        val songRef = database.getReference("song")

            val songName = musicListPA[songPositio].name
            if (isLiked) {
                isLiked = false
                binding.likeBtn.setImageResource(R.drawable.like_btn)
                if (lIndex != -1) {
                    Liked_Songs.likedSongs.removeAt(lIndex)
                    userRef.child(songName.toString()).removeValue() // remove song from Firebase
                }
            } else {
                isLiked = true
                binding.likeBtn.setImageResource(R.drawable.full_liked_btn)
                Liked_Songs.likedSongs.add(musicListPA[songPositio])
                userRef.child(songName.toString()).setValue(true) // add song to Firebase
            }




    }

    private fun retrievePaymentID(): String? {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("PaymentID", null)
    }

}

interface MediaPlayerInterface {
    fun createMediaPlayer()
    fun setLayout()
}

