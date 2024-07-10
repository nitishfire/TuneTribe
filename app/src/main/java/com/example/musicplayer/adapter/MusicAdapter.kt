/*package com.example.musicplayer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayer.PlayerActivity
import com.example.musicplayer.PlaylistDetails
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ActivitySongListLayoutBinding
import com.example.musicplayer.fragment.HomeFragment
import com.example.musicplayer.fragment.SearchFragment
import com.example.musicplayer.model.Music
import com.example.musicplayer.playlist_main

class MusicAdapter(private val context: Context, private var musicList: ArrayList<Music>, private var playlistDeatails:Boolean = false, private var selectionActivity: Boolean=false) :
    RecyclerView.Adapter<MusicAdapter.MyHolder>() {

    class MyHolder(binding: ActivitySongListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.songNameMV
        val album = binding.songAlbumMV
        val image = binding.imageMV
        val duration = binding.songDuration
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.MyHolder {
        return MyHolder(
            ActivitySongListLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MusicAdapter.MyHolder, position: Int) {
        val music = musicList[position]
        holder.title.text = music.title
        holder.album.text = music.album

        // Format the duration as "mm:ss"
        val minutes = music.duration / 1000 / 60
        val seconds = music.duration / 1000 % 60
        val durationText = String.format("%02d:%02d", minutes, seconds)
        holder.duration.text = durationText

        // Print the image path for debugging
        Log.d("MusicAdapter", "Image Path: ${music.image}")

        // Load the image using Glide
        Glide.with(context)
            .load(Uri.parse(music.image))
            .placeholder(R.drawable.logo_white)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.image)

        when{
            playlistDeatails -> {
                holder.root.setOnClickListener{
                    sendIntent(ref = "PlaylistDetailsAdapter", pos =position)
                }
            }
            selectionActivity -> {
                holder.root.setOnClickListener{
                    if(addSong(musicList[position])){
                        holder.root.setBackgroundColor(ContextCompat.getColor(context, R.color.blue_fromlogo))
                    }else{
                        holder.root.setBackgroundColor(ContextCompat.getColor(context, R.color.sign_log_background_color))
                    }
                }
            }
            else ->
                holder.root.setOnClickListener {
                    when {
                        SearchFragment.dummy == 1 -> {
                            val intent = when {
                                SearchFragment.search -> Intent(context, PlayerActivity::class.java).apply {
                                    putExtra("index", position)
                                    putExtra("class", "MusicAdapterSearch")
                                }
                                SearchFragment.direct -> Intent(context, PlayerActivity::class.java).apply {
                                    putExtra("index", position)
                                    putExtra("class", "MusicSearchMain")
                                }
                                else -> null
                            }
                            intent?.let { ContextCompat.startActivity(context, it, null) }
                        }
                        else -> {
                            val intent = Intent(context, PlayerActivity::class.java)
                            intent.putExtra("index", position)
                            intent.putExtra("class", "MusicAdapter")
                            ContextCompat.startActivity(context, intent, null)
                        }
                    }
                }


        }




    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMusicList(searchList: ArrayList<Music>){
        musicList = ArrayList()
        musicList.addAll(searchList)
        notifyDataSetChanged()
    }

    private fun sendIntent(ref: String, pos: Int){
        val intent = Intent(context, PlayerActivity::class.java)
        intent.putExtra("index", pos)
        intent.putExtra("class", ref)
        context.startActivity(intent)
    }

    private fun addSong(song: Music): Boolean{
        playlist_main.musicPlaylist.ref[PlaylistDetails.currentPlaylistPos].playlist.forEachIndexed{
                index, music ->
            if(song.id==music.id){
                playlist_main.musicPlaylist.ref[PlaylistDetails.currentPlaylistPos].playlist.removeAt(index)
                return false
            }
        }
        playlist_main.musicPlaylist.ref[PlaylistDetails.currentPlaylistPos].playlist.add(song)
        return true
    }

    fun refreshPlaylist(){
        musicList = ArrayList()
        musicList = playlist_main.musicPlaylist.ref[PlaylistDetails.currentPlaylistPos].playlist
        notifyDataSetChanged()
    }
}
*/

package com.example.musicplayer.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayer.PlayerActivity
import com.example.musicplayer.PlaylistDetails
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ActivitySongListLayoutBinding
import com.example.musicplayer.fragment.SearchFragment
import com.example.musicplayer.model.Music
import com.example.musicplayer.playlist_main

class MusicAdapter(private val context: Context, private var musicList: MutableList<Music>, private var playlistDeatails: Boolean = false, private var selectionActivity: Boolean = false) : RecyclerView.Adapter<MusicAdapter.MyHolder>() {

    class MyHolder(binding: ActivitySongListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.songNameMV
        val artist = binding.songAlbumMV
        val image = binding.imageMV
        val duration = binding.songDuration
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.MyHolder {
        return MyHolder(ActivitySongListLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MusicAdapter.MyHolder, position: Int) {
        val music = musicList[position]
        holder.name.text = music.name
        holder.artist.text = music.artist

        // Format the duration as "mm:ss"
        val m_d = music.duration!!.toInt()
        val minutes = m_d / 1000 / 60
        val seconds = m_d / 1000 % 60
        val durationText = String.format("%02d:%02d", minutes, seconds)
        holder.duration.text = durationText

        // Load the image using Glide
        Glide.with(context)
            .load(Uri.parse(music.image))
            .placeholder(R.drawable.logo_white)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.image)

    // Click listener for opening Player Activity
        holder.root.setOnClickListener {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra("index", position)
            intent.putExtra("class", "MusicAdapter")
            context.startActivity(intent)
        }

        // Click listener for opening Playlist Details
        if (playlistDeatails) {
            holder.root.setOnClickListener {
                sendIntent("PlaylistDetailsAdapter", position)
            }
        }


        when {
            playlistDeatails -> {
                holder.root.setOnClickListener {
                    sendIntent(ref = "PlaylistDetailsAdapter", pos = position)
                }
            }

            selectionActivity -> {
                holder.root.setOnClickListener {
                    if (addSong(musicList[position])) {
                        holder.root.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.blue_fromlogo
                            )
                        )
                    } else {
                        holder.root.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.sign_log_background_color
                            )
                        )
                    }
                }
            }
            else ->
                holder.root.setOnClickListener {
                    when {
                        SearchFragment.dummy == 1 -> {
                            val intent = when {
                                SearchFragment.search -> Intent(
                                    context,
                                    PlayerActivity::class.java
                                ).apply {
                                    putExtra("index", position)
                                    putExtra("class", "MusicAdapterSearch")
                                }

                                SearchFragment.direct -> Intent(
                                    context,
                                    PlayerActivity::class.java
                                ).apply {
                                    putExtra("index", position)
                                    putExtra("class", "MusicSearchMain")
                                }

                                else -> null
                            }
                            intent?.let { ContextCompat.startActivity(context, it, null) }
                        }

                        else -> {
                            val intent = Intent(context, PlayerActivity::class.java)
                            intent.putExtra("index", position)
                            intent.putExtra("class", "MusicAdapter")
                            ContextCompat.startActivity(context, intent, null)
                        }
                    }
                }
        }


        // Click listener for selection activity
        if (selectionActivity) {
            holder.root.setOnClickListener {
                if (addSong(musicList[position])) {
                    holder.root.setBackgroundColor(ContextCompat.getColor(context, R.color.blue_fromlogo))
                } else {
                    holder.root.setBackgroundColor(ContextCompat.getColor(context, R.color.sign_log_background_color))
                }
            }
        }



    }

    fun updateMusicList(searchList: ArrayList<Music>){
        musicList = ArrayList()
        musicList.addAll(searchList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    private fun sendIntent(ref: String, pos: Int) {
        val intent = Intent(context, PlayerActivity::class.java)
        intent.putExtra("index", pos)
        intent.putExtra("class", ref)
        context.startActivity(intent)
    }

    private fun addSong(song: Music): Boolean {
        val playlist = playlist_main.musicPlaylist.ref[PlaylistDetails.currentPlaylistPos].playlist
        val iterator = playlist.iterator()
        while (iterator.hasNext()) {
            val music = iterator.next()
            if (song.name == music.name) {
                iterator.remove()
                return false
            }
        }
        playlist.add(song)
        return true
    }


    fun refreshPlaylist() {
        musicList.clear()
        musicList.addAll(playlist_main.musicPlaylist.ref[PlaylistDetails.currentPlaylistPos].playlist)
        notifyDataSetChanged()
    }
}


