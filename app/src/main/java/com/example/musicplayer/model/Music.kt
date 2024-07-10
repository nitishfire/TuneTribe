package com.example.musicplayer.model

import android.media.MediaMetadataRetriever
import com.example.musicplayer.Liked_Songs
import com.example.musicplayer.PlayerActivity
import java.util.concurrent.TimeUnit

/*import android.media.MediaMetadataRetriever
import com.example.musicplayer.Liked_Songs
import com.example.musicplayer.PlayerActivity
import java.util.concurrent.TimeUnit

data class Music(
    val id: String,
    val title: String,
    val image: String,
    val album: String,
    val duration: Long,
    val path: String
)
var flag: Int = 0

fun formatDuration(duration: Long): String {
    val minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
    val seconds =
        (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)) -
                minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES)
    return String.format("%02d:%02d", minutes, seconds)
}

fun getImageArt(path: String): ByteArray? {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(path)
    return retriever.embeddedPicture
}

fun setSongPosition(increment: Boolean) {
    if (!PlayerActivity.repeat) {
        if (increment) {
            if (PlayerActivity.musicListPA.size - 1 == PlayerActivity.songPositio)
                PlayerActivity.songPositio = 0
            else
                ++PlayerActivity.songPositio
        } else {
            if (0 == PlayerActivity.songPositio)
                PlayerActivity.songPositio = PlayerActivity.musicListPA.size - 1
            else
                --PlayerActivity.songPositio
        }
    }
}

// It tells you that any particular song exists in our Liked Song list or not
fun likedChecker(id: String): Int {
    PlayerActivity.isLiked = false
    Liked_Songs.likedSongs.forEachIndexed { index, music ->
        if (id == music.id) {
            PlayerActivity.isLiked = true
            return index
        }
    }
    return -1
}

// Playlist Codes

class Playlist {
    lateinit var name: String
    lateinit var playlist: ArrayList<Music>
    lateinit var createdBy: String
    lateinit var createdOn: String
    lateinit var image: String
}

class MusicPlaylist {
    var ref: ArrayList<Playlist> = ArrayList()
}*/

data class Music(
    var artist: String? = null,
    var duration: String? = null,
    var image: String? = null,
    var name: String? = null,
    var url: String? = null
)

// It tells you that any particular song exists in our Liked Song list or not
fun likedChecker(name: String?): Int {
    PlayerActivity.isLiked = false
    Liked_Songs.likedSongs.forEachIndexed { index, music ->
        if (name == music.name) {
            PlayerActivity.isLiked = true
            return index
        }
    }
    return -1
}

var flag: Int = 0

fun formatDuration(duration: Long): String {
    val minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
    val seconds =
        (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)) -
                minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES)
    return String.format("%02d:%02d", minutes, seconds)
}

fun setSongPosition(increment: Boolean) {
    if (!PlayerActivity.repeat) {
        if (increment) {
            if (PlayerActivity.musicListPA.size - 1 == PlayerActivity.songPositio)
                PlayerActivity.songPositio = 0
            else
                ++PlayerActivity.songPositio
        } else {
            if (0 == PlayerActivity.songPositio)
                PlayerActivity.songPositio = PlayerActivity.musicListPA.size - 1
            else
                --PlayerActivity.songPositio
        }
    }
}

// Playlist Codes

class Playlist {
    lateinit var name: String
    lateinit var playlist: ArrayList<Music>
    lateinit var createdBy: String
    lateinit var createdOn: String
    lateinit var image: String
}

class MusicPlaylist {
    var ref: ArrayList<Playlist> = ArrayList()
}

fun getImageArt(path: String): ByteArray? {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(path)
    return retriever.embeddedPicture
}