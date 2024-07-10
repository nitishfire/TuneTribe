package com.example.musicplayer.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.musicplayer.DeviceSongs
import com.example.musicplayer.Liked_Songs
import com.example.musicplayer.MainActivity
import com.example.musicplayer.databinding.FragmentLibraryBinding
import com.example.musicplayer.model.flag
import com.example.musicplayer.playlist_main

class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.button10.setOnClickListener {
            val intent = Intent(requireContext(), Liked_Songs::class.java)
            startActivity(intent)
        }
        binding.button11.setOnClickListener {
            val intent = Intent(requireContext(), playlist_main::class.java)
            startActivity(intent)
        }
        binding.imageButton8.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        binding.button13.setOnClickListener {
            flag=10
            val intent = Intent(requireContext(), DeviceSongs::class.java)
            startActivity(intent)
        }

        return view
    }

    companion object {
        // You can add companion object properties or functions here if needed.
    }
}
