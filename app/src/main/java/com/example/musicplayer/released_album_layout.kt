package com.example.musicplayer

/*import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.MusicAdapter
import com.example.musicplayer.databinding.ActivityReleasedAlbumLayoutBinding
import com.example.musicplayer.model.Music
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class released_album_layout : AppCompatActivity() {
    private lateinit var musicAdapter: MusicAdapter
    private val binding: ActivityReleasedAlbumLayoutBinding by lazy {
        ActivityReleasedAlbumLayoutBinding.inflate(layoutInflater)
    }

    companion object {
        lateinit var musicListMA: ArrayList<Music>
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imageButton11.setOnClickListener{
            val intent  = Intent(this, PlayerActivity::class.java)
            intent.putExtra("index", 0)
            intent.putExtra("class", "released_album_layout")
            startActivity(intent)
        }


        // Copy assets only if the "Released" folder doesn't exist
        if (!isRFolderExists()) {
            downloadFolder()
        }

        // Get songs after ensuring the "Released" folder exists
        musicListMA = getallaudioo()

        // Initialize musicAdapter before calling notifyDataSetChanged
        musicAdapter = MusicAdapter(this@released_album_layout, musicListMA)

        // Set up RecyclerView
        binding.musicRVinAlbum.setHasFixedSize(true)
        binding.musicRVinAlbum.setItemViewCacheSize(20)
        binding.musicRVinAlbum.layoutManager = LinearLayoutManager(this@released_album_layout)
        binding.musicRVinAlbum.adapter = musicAdapter

        binding.imageButton3.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        supportActionBar?.hide()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    //List All The Songs From Released Folder To RecyclerView
    //Start
    @SuppressLint("Range")
    private fun getallaudioo(): ArrayList<Music> {
        val tempList = ArrayList<Music>()

        // Specify the directory path
        val musicDirPath = "${Environment.DIRECTORY_DOWNLOADS}/Released"

        // Note: Use `MediaStore.Audio.Media.RELATIVE_PATH` to filter by the relative path
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0 AND ${MediaStore.Audio.Media.RELATIVE_PATH} LIKE ?"
        val selectionArgs = arrayOf("%$musicDirPath%")

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID
        )

        val cursor = this.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            MediaStore.Audio.Media.DATE_ADDED
        )

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val titleC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)) ?: "Unknown"
                    val id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val artistC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)) ?: "Unknown"
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val durationC =
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val albumIdColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)

                    val albumIdC = if (albumIdColumnIndex != -1) {
                        cursor.getLong(albumIdColumnIndex).toString()
                    } else {
                        "Unknown"
                    }

                    val uri = Uri.parse("content://media/external/audio/albumart")
                    val artUriC = Uri.withAppendedPath(uri, albumIdC).toString()
                    val music = Music(
                        title = titleC,
                        album = artistC,
                        path = pathC,
                        duration = durationC,
                        image = artUriC,
                        id=id
                    )

                    // Check if the music file exists before adding to the list
                    val file = File(music.path)
                    if (file.exists()) {
                        tempList.add(music)
                    }
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return tempList
    }

    private fun isRFolderExists(): Boolean {
        val musicDir = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/Released")
        return musicDir.exists() && musicDir.isDirectory
    }
    //End







    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference

    fun downloadFolder() {
        val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val loFiFolder = File(downloadDir, "Released")

        if (!loFiFolder.exists()) {
            loFiFolder.mkdirs()
        }

        val loFiStorageRef: StorageReference = storageReference.child("Released")

        loFiStorageRef.listAll().addOnSuccessListener { listResult ->
            listResult.items.forEach { item ->
                val localFile = File(loFiFolder, item.name)

                item.getFile(localFile).addOnSuccessListener {
                    Log.d("FirebaseStorage", "File downloaded: ${localFile.absolutePath}")
                }.addOnFailureListener { exception ->
                    Log.e("FirebaseStorage", "Error downloading file", exception)
                }
            }
        }.addOnFailureListener { exception ->
            Log.e("FirebaseStorage", "Error listing files", exception)
        }
    }
    //End
}*/

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.MusicAdapter
import com.example.musicplayer.databinding.ActivityReleasedAlbumLayoutBinding
import com.example.musicplayer.model.Music
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class released_album_layout : AppCompatActivity() {
    private lateinit var musicAdapter: MusicAdapter
    private val binding: ActivityReleasedAlbumLayoutBinding by lazy {
        ActivityReleasedAlbumLayoutBinding.inflate(layoutInflater)
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
            intent.putExtra("class", "released_album_layout")
            startActivity(intent)
        }

        // Set up RecyclerView
        binding.musicRVinAlbum.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@released_album_layout)
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
        val databaseReference = FirebaseDatabase.getInstance().getReference("song/Released")

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