package com.example.musicplayer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.musicplayer.adapter.MusicAdapter
import com.example.musicplayer.databinding.ActivityPlaylistDetailsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PlaylistDetails : AppCompatActivity() {
    lateinit var binding: ActivityPlaylistDetailsBinding
    lateinit var adapter: MusicAdapter
    companion object{
        var currentPlaylistPos : Int = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistDetailsBinding.inflate(layoutInflater)


        currentPlaylistPos = intent.extras?.get("index") as Int

        binding.musicRVinPlaylist.setItemViewCacheSize(10)
        binding.musicRVinPlaylist.setHasFixedSize(true)
        binding.musicRVinPlaylist.layoutManager = LinearLayoutManager(this)
        adapter = MusicAdapter(this, playlist_main.musicPlaylist.ref[currentPlaylistPos].playlist, playlistDeatails = true)
        binding.musicRVinPlaylist.adapter = adapter
        binding.imageButton6.setOnClickListener{
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("index", 0)
            intent.putExtra("class", "PlaylistDetailsShuffle")
            startActivity(intent)
        }


        supportActionBar?.hide()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding.imageButton3.setOnClickListener{
            val intent = Intent(this, playlist_main::class.java)
            moveTaskToBack(true)
            startActivity(intent)
        }

        binding.imageButton12.setOnClickListener{
            startActivity(Intent(this, SelectionActivity::class.java))
        }
        binding.imageButton11.setOnClickListener{
            val builder = MaterialAlertDialogBuilder(this)
            builder.setTitle("Remove")
                .setMessage("Do You Want To Remove All Songs From Playlist?")
                .setPositiveButton("Yes") { dialog, _ ->
                    playlist_main.musicPlaylist.ref[currentPlaylistPos].playlist.clear()
                    adapter.refreshPlaylist()
                    dialog.dismiss()
                }.setNegativeButton("No"){
                    dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }

        setContentView(binding.root)
    }


    @SuppressLint("MissingSuperCall", "SetTextI18n", "NotifyDataSetChanged")
    override fun onResume(){
        super.onResume()
            binding.textView3.text = playlist_main.musicPlaylist.ref[currentPlaylistPos].name
            binding.textView10.text = "Created On: ${playlist_main.musicPlaylist.ref[currentPlaylistPos].createdOn}"
            binding.textView2.text = "From '${playlist_main.musicPlaylist.ref[currentPlaylistPos].createdBy}'"

        if(adapter.itemCount>0){
            // Load the image using Glide
            Glide.with(this)
                .load(playlist_main.musicPlaylist.ref[currentPlaylistPos].playlist[0].image)
                .placeholder(R.drawable.logo_white)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.albumPhoto)

            binding.imageButton6.visibility = View.VISIBLE
        }
        adapter.notifyDataSetChanged()
    }

}