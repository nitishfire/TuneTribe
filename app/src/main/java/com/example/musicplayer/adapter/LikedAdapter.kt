package com.example.musicplayer.adapter

/*import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayer.PlayerActivity
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ActivitySongListLayoutBinding
import com.example.musicplayer.fragment.SearchFragment
import com.example.musicplayer.model.Music

class LikedAdapter(private val context: Context, private var musicList: ArrayList<Music>) :
    RecyclerView.Adapter<LikedAdapter.MyHolder>() {

    class MyHolder(binding: ActivitySongListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.imageMV
        val name = binding.songNameMV
        val album = binding.songAlbumMV
        val duration = binding.songDuration
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedAdapter.MyHolder {
        return MyHolder(
            ActivitySongListLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val music =musicList[position]
        holder.name.text = musicList[position].title
        holder.album.text = musicList[position].album

        // Format the duration as "mm:ss"
        val minutes = music.duration / 1000 / 60
        val seconds = music.duration / 1000 % 60
        val durationText = String.format("%02d:%02d", minutes, seconds)
        holder.duration.text = durationText


        // Load the image using Glide
        Glide.with(context)
            .load(Uri.parse(music.image))
            .placeholder(R.drawable.logo_white)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.image)


        holder.root.setOnClickListener{
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra("index", position)
            intent.putExtra("class", "LikedAdapter")
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

}*/
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayer.PlayerActivity
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ActivitySongListLayoutBinding
import com.example.musicplayer.model.Music

class LikedAdapter(private val context: Context, private var musicList: ArrayList<Music>) :
    RecyclerView.Adapter<LikedAdapter.MyHolder>() {

    class MyHolder(binding: ActivitySongListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.imageMV
        val name = binding.songNameMV
        val album = binding.songAlbumMV
        val duration = binding.songDuration
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedAdapter.MyHolder {
        return MyHolder(
            ActivitySongListLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val music =musicList[position]
        holder.name.text = musicList[position].name
        holder.album.text = musicList[position].artist

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


        holder.root.setOnClickListener{
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra("index", position)
            intent.putExtra("class", "LikedAdapter")
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

}

