package com.example.musicplayer

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.MusicAdapter
import com.example.musicplayer.databinding.ActivityDeviceSongsBinding
import com.example.musicplayer.model.Music

class DeviceSongs : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceSongsBinding
    private lateinit var musicAdapter: MusicAdapter

    companion object {
        lateinit var musicListMA: ArrayList<Music>
    }

            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceSongsBinding.inflate(layoutInflater)
        setContentView(binding.root)


                // Get songs after ensuring the "Charlie Puth" folder exists
                musicListMA = getallaudio()

                // Initialize musicAdapter before calling notifyDataSetChanged
                musicAdapter = MusicAdapter(this@DeviceSongs, musicListMA)

                // Set up RecyclerView
                binding.DeviceRV.setHasFixedSize(true)
                binding.DeviceRV.setItemViewCacheSize(20)
                binding.DeviceRV.layoutManager = LinearLayoutManager(this@DeviceSongs)
                binding.DeviceRV.adapter = musicAdapter

                binding.imageButton8.setOnClickListener{
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

        supportActionBar?.hide()

        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
    @SuppressLint("Range")
    fun getallaudio(): ArrayList<Music> {
        val tempList = ArrayList<Music>()

        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID
        )

        val cursor = this.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            MediaStore.Audio.Media.DATE_ADDED
        )

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)) ?: "Unknown"
                    val artistC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)) ?: "Unknown"
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val durationC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val albumIdColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
                    val albumIdC = if (albumIdColumnIndex != -1) {
                        cursor.getLong(albumIdColumnIndex).toString()
                    } else {
                        "Unknown"
                    }
                    val uri = Uri.parse("content://media/external/audio/albumart")
                    val artUriC = Uri.withAppendedPath(uri, albumIdC).toString()
                    val music = Music(
                        name = titleC,
                        artist = artistC,
                        url = pathC,
                        duration = durationC.toString(),
                        image = artUriC,
                    )
                    tempList.add(music)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return tempList
    }
}