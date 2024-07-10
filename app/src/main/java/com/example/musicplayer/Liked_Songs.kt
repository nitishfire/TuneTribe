package com.example.musicplayer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.LikedAdapter
import com.example.musicplayer.databinding.ActivityLikedSongsBinding
import com.example.musicplayer.model.Music
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Liked_Songs : AppCompatActivity() {
    private lateinit var binding: ActivityLikedSongsBinding
    private lateinit var adapter: LikedAdapter

    companion object{
        var likedSongs: ArrayList<Music> = ArrayList()
    }



    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikedSongsBinding.inflate(layoutInflater)

        binding.LikedRV.setHasFixedSize(true)
        binding.LikedRV.setItemViewCacheSize(20)
        binding.LikedRV.layoutManager = LinearLayoutManager(this@Liked_Songs)
        adapter = LikedAdapter(this, likedSongs)
        binding.LikedRV.adapter = adapter

        liked_song_retrieve()

        setContentView(binding.root)

        binding.imageButton8.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            moveTaskToBack(true)
            startActivity(intent)
        }

        supportActionBar?.hide()

        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }


    private fun liked_song_retrieve() {
        val database = FirebaseDatabase.getInstance()
        val userId = retrievePaymentID()
        val userRef = database.getReference("user").child(userId!!).child("liked_songs")
        val songRef = database.getReference("song")

        // Check if any song exists in the database for the user
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val likedSongName = snapshot.key // Get the full song name including the artist name
                    // Search all songs in 'song' root
                    songRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (artistSnapshot in snapshot.children) { // Iterate over each artist
                                for (songSnapshot in artistSnapshot.children) { // Iterate over each song of the artist
                                    val song = songSnapshot.getValue(Music::class.java)
                                    // If the song name matches with the liked song name and it's not already in the list, add it to liked songs
                                    if (song?.name == likedSongName && !likedSongs.contains(song)) {
                                        likedSongs.add(song!!)
                                        adapter.notifyDataSetChanged() // Notify the adapter about the new item
                                    }
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle error
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }





    private fun retrievePaymentID(): String? {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("PaymentID", null)
    }

}
