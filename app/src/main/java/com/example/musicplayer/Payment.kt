package com.example.musicplayer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplayer.databinding.ActivityPaymentBinding
import com.google.firebase.database.FirebaseDatabase
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class Payment : AppCompatActivity(), PaymentResultWithDataListener {
    private lateinit var GA: String
    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Checkout.preload(applicationContext)
        val co = Checkout()
        co.setKeyID("rzp_test_9TowzxJn5hUY7p")

        val btn = findViewById<Button>(R.id.button5)

        btn.setOnClickListener {
            startPayment()
        }

        binding.imageButton8.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }


    private fun startPayment() {
        val activity: Activity = this
        val co = Checkout()

        try {
            val packageName = applicationContext.packageName
            val amountStr = findViewById<EditText>(R.id.editTextText).text.toString()

            if (amountStr.isNotEmpty()) {
                val amount = (amountStr.toInt() * 100).toString()
                GA = amountStr
                val options = JSONObject()
                options.put("name", "TuneTribe")
                options.put("description", "Support The Platform")
                options.put("image", "android.resource://${packageName}/${R.drawable.music_app_logo_black_trans}")
                options.put("theme.color", "#3399cc")
                options.put("currency", "INR")
                options.put("amount", amount)

                val retryObj = JSONObject()
                retryObj.put("enabled", true)
                retryObj.put("max_count", 4)
                options.put("retry", retryObj)

                val prefill = JSONObject()
                prefill.put("email", "nitish.mudaliar125@gmail.com")
                prefill.put("contact", "7041897117")

                options.put("prefill", prefill)
                co.open(activity, options)
            } else {
                Toast.makeText(activity, "Please enter a valid amount", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this, "Error: ${p1}", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        val PaymentID = retrievePaymentID()
        if (PaymentID != null) {
            val database = FirebaseDatabase.getInstance()
            val userId = PaymentID
            Toast.makeText(this, "Payment Success :) $userId", Toast.LENGTH_SHORT).show()
            val userRef = database.getReference("user").child(userId).child("supported_amount")
            userRef.push().setValue(GA)
        } else {
            Toast.makeText(this, "PaymentID not found", Toast.LENGTH_SHORT).show()
        }
    }

    // Retrieving PaymentID from SharedPreferences
    private fun retrievePaymentID(): String? {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("PaymentID", null)
    }

}
