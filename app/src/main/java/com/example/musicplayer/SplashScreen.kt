package com.example.musicplayer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayInputStream

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        DataRetrieve()


        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler().postDelayed({
            val intent = Intent(this, Welcome_Screen::class.java)
            startActivity(intent)
            finish()
        },1000)
    }

    fun DataRetrieve() {
        val storage = FirebaseStorage.getInstance()
        val database = FirebaseDatabase.getInstance().getReference("song")

        storage.reference.listAll()
            .addOnSuccessListener { listResult ->
                listResult.prefixes.forEach { folder ->
                    folder.listAll()
                        .addOnSuccessListener { songs ->
                            songs.items.forEach { song ->
                                // Check if the song data already exists in the database
                                database.child(folder.name).orderByChild("name").equalTo(song.name.removeSuffix(".mp3"))
                                    .addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                                            if (!dataSnapshot.exists()) {
                                                // If the song data does not exist, retrieve and upload it
                                                song.downloadUrl.addOnSuccessListener { url ->
                                                    val songName = song.name.removeSuffix(".mp3")

                                                    val mmr = MediaMetadataRetriever()
                                                    mmr.setDataSource(url.toString(), HashMap())

                                                    val songArtist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
                                                    val songDuration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                                                    val albumArt = mmr.embeddedPicture // This is a byte array

                                                    // Upload the album art to Firebase Storage
                                                    val imageRef = storage.reference.child("song images/${folder.name}/$songName")
                                                    val uploadTask = imageRef.putStream(ByteArrayInputStream(albumArt))
                                                    uploadTask.continueWithTask { task ->
                                                        if (!task.isSuccessful) {
                                                            task.exception?.let {
                                                                throw it
                                                            }
                                                        }
                                                        imageRef.downloadUrl
                                                    }.addOnCompleteListener { task ->
                                                        if (task.isSuccessful) {
                                                            val downloadUrl = task.result

                                                            val songData = mapOf(
                                                                "name" to songName,
                                                                "url" to url.toString(),
                                                                "artist" to songArtist,
                                                                "image" to downloadUrl.toString(),
                                                                "duration" to songDuration
                                                            )

                                                            database.child(folder.name).push().setValue(songData)
                                                        } else {
                                                            // Handle failure
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        override fun onCancelled(databaseError: DatabaseError) {
                                            // Handle error
                                        }
                                    })
                            }
                        }
                }
            }
            .addOnFailureListener { exception ->
                // Log the error here
                Log.e("Firebase Error", exception.message, exception)
            }
    }




}



