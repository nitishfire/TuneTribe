package com.example.musicplayer.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayer.PlaylistDetails
import com.example.musicplayer.R
import com.example.musicplayer.databinding.PlaylistViewBinding
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.playlist_main
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PlaylistViewAdapter(private val context: Context, private var playlistList: List<Playlist>) :
    RecyclerView.Adapter<PlaylistViewAdapter.MyHolder>() {

    class MyHolder(binding: PlaylistViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.plimagePV
        val name = binding.playlistNamePV
        val root = binding.root
        val delete = binding.dustbinPV
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewAdapter.MyHolder {
        return MyHolder(
            PlaylistViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val music = playlistList[position]
        holder.name.text = music.name
        holder.name.isSelected = true
        holder.delete.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(context)
            builder.setTitle(playlistList[position].name)
                .setMessage("Do You Want To Delete Playlist?")
                .setPositiveButton("Yes") { dialog, _ ->
                    playlist_main.musicPlaylist.ref.removeAt(position)
                    refreshPlayList()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            val customDialog = builder.create()
            customDialog.show()
            customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.resources.getColor(R.color.blue_fromlogo))
            customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.resources.getColor(R.color.blue_fromlogo))
        }

        holder.root.setOnClickListener {
            val intent = Intent(context, PlaylistDetails::class.java)
            intent.putExtra("index", position)
            ContextCompat.startActivity(context, intent, null)
        }

        if(playlist_main.musicPlaylist.ref[position].playlist.size>0){
            Glide.with(context)
                .load(playlist_main.musicPlaylist.ref[position].playlist[0].image)
                .placeholder(R.drawable.logo_white)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.image)
        }
    }

    override fun getItemCount(): Int {
        return playlistList.size
    }

    fun refreshPlayList() {
        playlistList = playlist_main.musicPlaylist.ref.toList()
        notifyDataSetChanged()
    }

}
