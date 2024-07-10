package com.example.musicplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.PlaylistViewAdapter
import com.example.musicplayer.databinding.ActivityPlaylistMainBinding
import com.example.musicplayer.databinding.AddPlaylistDialogBinding
import com.example.musicplayer.databinding.FragmentLibraryBinding
import com.example.musicplayer.model.MusicPlaylist
import com.example.musicplayer.model.Playlist
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.Locale

class playlist_main : AppCompatActivity() {
    private lateinit var binding: ActivityPlaylistMainBinding
    private lateinit var adapter: PlaylistViewAdapter

    companion object {
        var musicPlaylist: MusicPlaylist = MusicPlaylist()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistMainBinding.inflate(layoutInflater)

        // Initialize adapter with an empty list initially
        adapter = PlaylistViewAdapter(this, musicPlaylist.ref)
        binding.playlistRV.setItemViewCacheSize(20)
        binding.playlistRV.layoutManager = LinearLayoutManager(this@playlist_main)
        binding.playlistRV.adapter = adapter

        binding.button11.setOnClickListener {
            customAlertDialog()
        }

        binding.imageButton8.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        supportActionBar?.hide()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(binding.root)
    }




    private fun customAlertDialog() {
        val customDialog = LayoutInflater.from(this@playlist_main).inflate(
            R.layout.add_playlist_dialog,
            binding.root,
            false
        )
        val binder = AddPlaylistDialogBinding.bind(customDialog)

        val builder = MaterialAlertDialogBuilder(this)
        builder.setView(customDialog)
            .setTitle("Playlist Details")
            .setPositiveButton("Add") { dialog, _ ->
                val playlistName = binder.playlistName.text
                val createdBy = binder.yourName.text
                if (playlistName != null && createdBy != null) {
                    if (playlistName.isNotEmpty() && createdBy.isNotEmpty()) {
                        addPlaylist(playlistName.toString(), createdBy.toString())

                        // Update the adapter with the new playlist list
                        adapter.refreshPlayList()
                    }
                }
                dialog.dismiss()
            }
            .create()
            .show()
    }


    private fun addPlaylist(name: String, createdBy: String) {
        var playlistExist = false
        for (i in musicPlaylist.ref) {
            if (name.equals(i.name)) {
                playlistExist = true
                break
            }
        }
        if (playlistExist) {
            Toast.makeText(this, "Playlist Exist!!", Toast.LENGTH_SHORT).show()
        } else {
            val tempPlaylist = Playlist()
            tempPlaylist.name = name
            tempPlaylist.playlist = ArrayList()
            tempPlaylist.createdBy = createdBy

            val calendar = java.util.Calendar.getInstance().time
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            tempPlaylist.createdOn = sdf.format(calendar)
            musicPlaylist.ref.add(tempPlaylist)

            // Update the adapter with the new playlist list
            adapter.refreshPlayList()
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}
