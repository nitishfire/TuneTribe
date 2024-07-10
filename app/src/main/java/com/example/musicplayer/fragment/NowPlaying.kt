package com.example.musicplayer.fragment

/*import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.ui.window.isPopupLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.musicplayer.PlayerActivity
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentNowPlayingBinding
import com.example.musicplayer.model.setSongPosition

class NowPlaying : Fragment() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: FragmentNowPlayingBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)
        binding = FragmentNowPlayingBinding.bind(view)
        binding.root.visibility = View.INVISIBLE

        binding.NPPlay.setOnClickListener {
            if (PlayerActivity.isPlaying) pauseMusic() else playMusic()
        }

        binding.NPNext.setOnClickListener{
            setSongPosition(increment = true)
            PlayerActivity.musicService!!.mediaPlayerProp()
            Glide.with(this)
                .load(PlayerActivity.musicListPA[PlayerActivity.songPositio].image)
                .placeholder(R.drawable.logo_white)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.NPimage)
            binding.textView9.text = PlayerActivity.musicListPA[PlayerActivity.songPositio].title
            PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon, 0F)
            playMusic()
        }

        binding.NPBack.setOnClickListener{
            setSongPosition(increment = false)
            PlayerActivity.musicService!!.mediaPlayerProp()
            Glide.with(this)
                .load(PlayerActivity.musicListPA[PlayerActivity.songPositio].image)
                .placeholder(R.drawable.logo_white)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.NPimage)
            binding.textView9.text = PlayerActivity.musicListPA[PlayerActivity.songPositio].title
            PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon, 0F)
            playMusic()
        }

        binding.root.setOnClickListener{
            val intent = Intent(requireContext(), PlayerActivity::class.java)
            intent.putExtra("index", PlayerActivity.songPositio)
            intent.putExtra("class", "NowPlaying")
            ContextCompat.startActivity(requireContext(), intent, null)
        }
        return view
    }


    override fun onResume() {
        super.onResume()
        if (PlayerActivity.musicService != null) {
            binding.root.visibility = View.VISIBLE
            binding.textView9.isSelected=true
            Glide.with(this)
                .load(PlayerActivity.musicListPA[PlayerActivity.songPositio].image)
                .placeholder(R.drawable.logo_white)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.NPimage)

            binding.textView9.text = PlayerActivity.musicListPA[PlayerActivity.songPositio].title
            if (PlayerActivity.isPlaying) binding.NPPlay.setImageResource(R.drawable.pause_icon)
            else binding.NPPlay.setImageResource(R.drawable.play_arrow)
        }
    }


    private fun playMusic() {
        PlayerActivity.musicService?.mediaPlayer!!.start()
        binding.NPPlay.setImageResource(R.drawable.pause_icon)
        PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon, 0F)
        PlayerActivity.binding.floatingActionButton2.setImageResource(R.drawable.pause_icon)
        PlayerActivity.isPlaying = true
    }

    private fun pauseMusic() {
        PlayerActivity.musicService?.mediaPlayer!!.pause()
        binding.NPPlay.setImageResource(R.drawable.play_arrow)
        PlayerActivity.musicService!!.showNotification(R.drawable.play_arrow, 0F)
        PlayerActivity.binding.floatingActionButton2.setImageResource(R.drawable.play_arrow)
        PlayerActivity.isPlaying = false
    }

}*/

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.musicplayer.PlayerActivity
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentNowPlayingBinding
import com.example.musicplayer.model.setSongPosition

class NowPlaying : Fragment() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: FragmentNowPlayingBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)
        binding = FragmentNowPlayingBinding.bind(view)
        binding.root.visibility = View.INVISIBLE

        binding.NPPlay.setOnClickListener {
            if (PlayerActivity.isPlaying) pauseMusic() else playMusic()
        }

        binding.NPNext.setOnClickListener{
            setSongPosition(increment = true)
            PlayerActivity.musicService!!.mediaPlayerProp()
            Glide.with(this)
                .load(PlayerActivity.musicListPA[PlayerActivity.songPositio].image)
                .placeholder(R.drawable.logo_white)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.NPimage)
            binding.textView9.text = PlayerActivity.musicListPA[PlayerActivity.songPositio].name
            PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon, 0F)
            playMusic()
        }

        binding.NPBack.setOnClickListener{
            setSongPosition(increment = false)
            PlayerActivity.musicService!!.mediaPlayerProp()
            Glide.with(this)
                .load(PlayerActivity.musicListPA[PlayerActivity.songPositio].image)
                .placeholder(R.drawable.logo_white)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.NPimage)
            binding.textView9.text = PlayerActivity.musicListPA[PlayerActivity.songPositio].name
            PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon, 0F)
            playMusic()
        }

        binding.root.setOnClickListener{
            val intent = Intent(requireContext(), PlayerActivity::class.java)
            intent.putExtra("index", PlayerActivity.songPositio)
            intent.putExtra("class", "NowPlaying")
            ContextCompat.startActivity(requireContext(), intent, null)
        }
        return view
    }


    override fun onResume() {
        super.onResume()
        if (PlayerActivity.musicService != null) {
            binding.root.visibility = View.VISIBLE
            binding.textView9.isSelected=true
            Glide.with(this)
                .load(PlayerActivity.musicListPA[PlayerActivity.songPositio].image)
                .placeholder(R.drawable.logo_white)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.NPimage)

            binding.textView9.text = PlayerActivity.musicListPA[PlayerActivity.songPositio].name
            if (PlayerActivity.isPlaying) binding.NPPlay.setImageResource(R.drawable.pause_icon)
            else binding.NPPlay.setImageResource(R.drawable.play_arrow)
        }
    }


    private fun playMusic() {
        PlayerActivity.musicService?.mediaPlayer!!.start()
        binding.NPPlay.setImageResource(R.drawable.pause_icon)
        PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon, 0F)
        PlayerActivity.binding.floatingActionButton2.setImageResource(R.drawable.pause_icon)
        PlayerActivity.isPlaying = true
    }

    private fun pauseMusic() {
        PlayerActivity.musicService?.mediaPlayer!!.pause()
        binding.NPPlay.setImageResource(R.drawable.play_arrow)
        PlayerActivity.musicService!!.showNotification(R.drawable.play_arrow, 0F)
        PlayerActivity.binding.floatingActionButton2.setImageResource(R.drawable.play_arrow)
        PlayerActivity.isPlaying = false
    }

}

