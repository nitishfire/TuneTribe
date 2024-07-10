package com.example.musicplayer

/*import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.MusicAdapter
import com.example.musicplayer.databinding.LofiAlbumLayoutBinding
import com.example.musicplayer.model.Music
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class lofi_album_layout : AppCompatActivity() {

    private lateinit var musicAdapter: MusicAdapter
    private val binding: LofiAlbumLayoutBinding by lazy {
        LofiAlbumLayoutBinding.inflate(layoutInflater)
    }

    companion object {
        lateinit var musicListMA: ArrayList<Music>
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize the music list
        musicListMA = ArrayList<Music>()

        // Initialize the music adapter
        musicAdapter = MusicAdapter(this, musicListMA)

        binding.imageButton11.setOnClickListener{
            val intent  = Intent(this, PlayerActivity::class.java)
            intent.putExtra("index", 0)
            intent.putExtra("class", "lofi_album_layout")
            startActivity(intent)
        }

        // Set up RecyclerView
        binding.musicRVinAlbum.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@lofi_album_layout)
            adapter = musicAdapter
        }

        // Retrieve Music Data from Firebase Realtime Database
        retrieveMusicData()

        binding.imageButton3.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun retrieveMusicData() {
        // Reference to the Firebase Realtime Database
        val databaseReference = FirebaseDatabase.getInstance().getReference("song/LoFi")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear the list before adding new data
                musicListMA.clear()

                for (musicSnapshot in snapshot.children) {
                    // Create a Music object from the snapshot
                    val music = musicSnapshot.getValue(Music::class.java)

                    if (music != null) {
                        // Add the Music object to the list
                        musicListMA.add(music)
                    }
                }

                // Notify that data has changed
                musicAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Error", error.message)
            }
        })
    }
}*/

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.MusicAdapter
import com.example.musicplayer.databinding.LofiAlbumLayoutBinding
import com.example.musicplayer.model.Music
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class lofi_album_layout : AppCompatActivity() {

    private lateinit var musicAdapter: MusicAdapter
    private val binding: LofiAlbumLayoutBinding by lazy {
        LofiAlbumLayoutBinding.inflate(layoutInflater)
    }

    companion object {
        lateinit var musicListMA: ArrayList<Music>
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Thread.setDefaultUncaughtExceptionHandler { _, _ ->
            // Catch the exception and do nothing
        }

        // Initialize the music list
        musicListMA = ArrayList<Music>()

        // Initialize the music adapter
        musicAdapter = MusicAdapter(this, musicListMA)

        binding.imageButton11.setOnClickListener{
            val intent  = Intent(this, PlayerActivity::class.java)
            intent.putExtra("index", 0)
            intent.putExtra("class", "lofi_album_layout")
            startActivity(intent)
        }

        // Set up RecyclerView
        binding.musicRVinAlbum.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@lofi_album_layout)
            adapter = musicAdapter
        }

        // Retrieve Music Data from Firebase Realtime Database
        retrieveMusicData()

        binding.imageButton3.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun retrieveMusicData() {
        // Reference to the Firebase Realtime Database
        val databaseReference = FirebaseDatabase.getInstance().getReference("song/LoFi")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear the list before adding new data
                musicListMA.clear()

                for (musicSnapshot in snapshot.children) {
                    // Create a Music object from the snapshot
                    val music = musicSnapshot.getValue(Music::class.java)

                    if (music != null) {
                        // Add the Music object to the list
                        musicListMA.add(music)
                    }
                }

                // Notify that data has changed
                musicAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Error", error.message)
            }
        })
    }
}