package com.example.musicplayer

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplayer.databinding.ActivityProfileBinding
import com.example.musicplayer.fragment.HomeFragment.Companion.signoutFlag

class Profile : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // Back button On Profile
        binding.imageButton8.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Retrieve user information from SharedPreferences
        val storedUsername = sharedPreferences.getString(KEY_USERNAME, null)
        val storedEmail = sharedPreferences.getString(KEY_EMAIL, null)

        // If stored values are available, use them
        if (storedUsername != null && storedEmail != null) {
            binding.textView15.text = storedUsername
            binding.textView19.text = storedEmail
        }

        // Sign Out Button
        binding.button11.setOnClickListener {
            // Clear SharedPreferences on sign out
            sharedPreferences.edit().clear().apply()

            signoutFlag = 1
            val intent = Intent(this, SignUp::class.java)
            finish()
            startActivity(intent)
        }

        //About Page
        binding.button12.setOnClickListener {
            val intent = Intent(this, About::class.java)
            finish()
            startActivity(intent)
        }

        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    companion object {
        private const val PREF_NAME = "UserProfile"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
    }
}
