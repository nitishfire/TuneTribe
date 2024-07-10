package com.example.musicplayer

/*import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.MusicAdapter
import com.example.musicplayer.databinding.ActivitySelectionBinding
import com.example.musicplayer.fragment.SearchFragment

class SelectionActivity : AppCompatActivity() {
    lateinit var binding: ActivitySelectionBinding
    lateinit var adapter: MusicAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectionBinding.inflate(layoutInflater)

        binding.imageButton8.setOnClickListener{
            finish()
        }

        binding.musicRVinSelect.setItemViewCacheSize(50)
        binding.musicRVinSelect.setHasFixedSize(true)
        binding.musicRVinSelect.layoutManager = LinearLayoutManager(this)
        adapter = MusicAdapter(this, SearchFragment.musicListMA1, selectionActivity = true)
        binding.musicRVinSelect.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle the submission if needed
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SearchFragment", "onQueryTextChange: $newText")
                SearchFragment.musicListSearch = ArrayList()

                if (newText != null) {
                    val userInput = newText.lowercase()
                    for (song in SearchFragment.musicListMA1) {
                        if (song.title.lowercase().contains(userInput)) {
                            SearchFragment.musicListSearch.add(song)
                        }
                    }
                    SearchFragment.search = true
                    adapter.updateMusicList(searchList = SearchFragment.musicListSearch)
                }
                return true
            }
        })

        setContentView(binding.root)
        supportActionBar?.hide()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}*/

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.MusicAdapter
import com.example.musicplayer.databinding.ActivitySelectionBinding
import com.example.musicplayer.fragment.SearchFragment

class SelectionActivity : AppCompatActivity() {
    lateinit var binding: ActivitySelectionBinding
    lateinit var adapter: MusicAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectionBinding.inflate(layoutInflater)

        binding.imageButton8.setOnClickListener{
            finish()
        }

        binding.musicRVinSelect.setItemViewCacheSize(50)
        binding.musicRVinSelect.setHasFixedSize(true)
        binding.musicRVinSelect.layoutManager = LinearLayoutManager(this)
        adapter = MusicAdapter(this, SearchFragment.musicListMA1, selectionActivity = true)
        binding.musicRVinSelect.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle the submission if needed
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SearchFragment", "onQueryTextChange: $newText")
                SearchFragment.musicListSearch = ArrayList()

                if (newText != null) {
                    val userInput = newText.lowercase()
                    for (song in SearchFragment.musicListMA1) {
                        if (song.name!!.lowercase().contains(userInput)) {
                            SearchFragment.musicListSearch.add(song)
                        }
                    }
                    SearchFragment.search = true
                    adapter.updateMusicList(searchList = SearchFragment.musicListSearch)
                }
                return true
            }
        })

        setContentView(binding.root)
        supportActionBar?.hide()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}