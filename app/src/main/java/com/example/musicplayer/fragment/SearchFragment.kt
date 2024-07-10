package com.example.musicplayer.fragment

/*import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.MusicAdapter
import com.example.musicplayer.databinding.FragmentSearchBinding
import com.example.musicplayer.model.Music
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class SearchFragment : Fragment() {
    private val storage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = storage.reference


    private lateinit var musicAdapter: MusicAdapter
    private val binding: FragmentSearchBinding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }

    companion object {
        lateinit var musicListMA1: ArrayList<Music>
        var search: Boolean = false
        var direct: Boolean = false
        var dummy: Int = 0
        lateinit var musicListSearch: ArrayList<Music>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dummy=1
        initializeMusicList()
    }

    fun initializeMusicList() {
        musicListMA1 = ArrayList()
        musicListSearch = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setHasOptionsMenu(true)
        search = false
        // Get songs after ensuring the "Search Songs" folder exists
        musicListMA1 = getallaudio()

        // Initialize musicAdapter before calling notifyDataSetChanged
        musicAdapter = MusicAdapter(requireContext(), musicListMA1)

        // Set up RecyclerView
        binding.musicRVinAlbum.setHasFixedSize(true)
        binding.musicRVinAlbum.setItemViewCacheSize(110)
        binding.musicRVinAlbum.layoutManager = LinearLayoutManager(requireContext())
        direct=true
        binding.musicRVinAlbum.adapter = musicAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Get a reference to the SearchView from the layout
        val searchView = binding.searchView

        // Set up the search view
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle the submission if needed
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SearchFragment", "onQueryTextChange: $newText")
                musicListSearch = ArrayList()

                if (newText != null) {
                    val userInput = newText.lowercase()
                    for (song in musicListMA1) {
                        if (song.title.lowercase().contains(userInput)) {
                            musicListSearch.add(song)
                        }
                    }
                    search = true
                    musicAdapter.updateMusicList(searchList = musicListSearch)
                }
                return true
            }
        })
    }


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

        val cursor = requireContext().contentResolver.query(
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
                        title = titleC,
                        album = artistC,
                        path = pathC,
                        duration = durationC,
                        image = artUriC,
                        id=id
                    )
                    tempList.add(music)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return tempList
    }

}*/
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.adapter.MusicAdapter
import com.example.musicplayer.databinding.FragmentSearchBinding
import com.example.musicplayer.model.Music
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {
    private lateinit var musicAdapter: MusicAdapter
    private val binding: FragmentSearchBinding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }

    companion object {
        var musicListMA1: ArrayList<Music> = ArrayList()
        var search: Boolean = false
        var direct: Boolean = false
        var dummy: Int = 0
        lateinit var musicListSearch: ArrayList<Music>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dummy = 1
        initializeMusicList()
    }

    fun initializeMusicList() {
        musicListMA1 = ArrayList()
        musicListSearch = ArrayList()
        dummy = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setHasOptionsMenu(true)
        search = false

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Initialize the music list and adapter
        musicListMA1 = ArrayList()
        musicAdapter = MusicAdapter(requireContext(), musicListMA1)

        // Set up RecyclerView
        binding.musicRVinAlbum.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = musicAdapter
        }

        // Retrieve Music Data from Firebase Realtime Database
        retrieveMusicData()
        direct = true


        dummy = 1
        return binding.root
    }

    private fun retrieveMusicData() {
        // Reference to the Firebase Realtime Database
        val databaseReference = FirebaseDatabase.getInstance().getReference("song")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear the list before adding new data
                musicListMA1.clear()

                for (subRootSnapshot in snapshot.children) {
                    for (musicSnapshot in subRootSnapshot.children) {
                        // Create a Music object from the snapshot
                        val music = musicSnapshot.getValue(Music::class.java)

                        if (music != null) {
                            // Add the Music object to the list
                            musicListMA1.add(music)
                        }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get a reference to the SearchView from the layout
        val searchView = binding.searchView

        // Set up the search view
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle the submission if needed
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SearchFragment", "onQueryTextChange: $newText")
                musicListSearch = ArrayList()

                if (newText != null) {
                    val userInput = newText.lowercase()
                    for (song in musicListMA1) {
                        if (song.name!!.lowercase().contains(userInput)) {
                            musicListSearch.add(song)
                        }
                    }
                    search = true
                    musicAdapter.updateMusicList(searchList = musicListSearch)
                }
                return true
            }
        })

    }
}

