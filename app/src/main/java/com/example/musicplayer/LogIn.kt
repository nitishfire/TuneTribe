package com.example.musicplayer


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplayer.databinding.ActivityLogInBinding
import com.example.musicplayer.fragment.HomeFragment.Companion.signoutFlag
import com.example.musicplayer.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


@Suppress("NAME_SHADOWING")
class LogIn : AppCompatActivity() {

    private val binding: ActivityLogInBinding by lazy {
        ActivityLogInBinding.inflate(layoutInflater)
    }

    companion object{
        var userGmail: String = " "
        var username: String = " "

        var emailSigned: String= " "
        var usernameSigned: String= " "

        var PaymentID: String? = null
    }


    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imageButton.setOnClickListener{
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        binding.button6.setOnClickListener{
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }


        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        //Initialization of Firebase Auth
        auth=Firebase.auth
        //Initialization of Firebase Database
        database = Firebase.database.reference
        //Initialization of Google
        googleSignInClient = GoogleSignIn.getClient(this, gso)


        //LogIn With Email And Password
        //Start
            binding.button5.setOnClickListener {
                email = binding.editTextText2.text.toString()
                password = binding.editTextTextPassword.text.toString()

                if (email.isEmpty() || password.isBlank()) {
                    Toast.makeText(this, "Please Enter All The Details", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    createUser()
                    Toast.makeText(this, "Login SuccessFull, Welcome to TuneTribe", Toast.LENGTH_SHORT).show()
                }
            }
        //End

        //Google LogIn
        //Start
            binding.imageButton2.setOnClickListener{
                val signinIntent = googleSignInClient.signInIntent
                launcher.launch(signinIntent)
            }
        //End

        supportActionBar?.hide()

        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        clearPaymentID()
    }


    //Google LogIn
    //Start
        private val launcher =  registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            result ->
            if (result.resultCode == Activity.RESULT_OK)
            {
                val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if(task.isSuccessful)
                {
                    val account: GoogleSignInAccount?=task.result
                    userGmail= account?.email!!
                    saveUserInfoToPrefs(username, userGmail)
                    extractAndStoreUsername(userGmail)
                    val credential= GoogleAuthProvider.getCredential(account?.idToken, null)

                    auth.signInWithCredential(credential).addOnCompleteListener{
                        if(it.isSuccessful)
                        {
                            Toast.makeText(this, "Welcome To TuneTribe, $username", Toast.LENGTH_LONG).show()
                            signoutFlag=0
                            //Redirect To Another Page If Sign Up Is Completed
                            startActivity(Intent(this, MainActivity::class.java))
                        }else{
                            Toast.makeText(this,"Failed To SignUp, Please Try Again Or Use Another Sign Up Options",Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }else{
                Toast.makeText(this,"Failed To SignUp, Please Try Again Or Use Another Sign Up Options",Toast.LENGTH_LONG).show()
            }
        }
    //End



    //Login With User And Password
    //Start
    private fun createUser(){
        var PaymentEmail: String
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                task ->
            if(task.isSuccessful){
                extractAndStoreUsernameInNormal(email)
                saveUserInfoToPrefs(usernameSigned, email)
                val user = auth.currentUser
                if (user != null) {
                    PaymentEmail = email
                    PaymentID = user.uid
                    storePaymentID(PaymentID!!)
                    Log.d("Login", "User email: $PaymentEmail")
                    Log.d("Login", "User ID: $PaymentID")
                } else {
                    Log.d("Login", "User is null")
                }
                updateUi(user)
            }else{
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        task ->
                    if(task.isSuccessful){
                        extractAndStoreUsernameInNormal(email)
                        saveUserInfoToPrefs(usernameSigned, email)
                        saveUserData()
                        signoutFlag=0
                        val user = auth.currentUser
                        if (user != null) {
                            PaymentID = user.uid
                            Log.d("AccountCreation", "User ID: $PaymentID")
                        } else {
                            Log.d("AccountCreation", "User is null")
                        }
                        updateUi(user)
                    }else{
                        Toast.makeText(this, "Log-In Failed Please Try Again Or Use Google Log-In", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }



    private fun saveUserData(){
        email=binding.editTextText2.text.toString()
        password=binding.editTextTextPassword.text.toString().trim()


        val user = UserModel(email, password)
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        //Sava Data To Database Firebase
        database.child("user").child(userID).setValue(user)
    }


    //Stay LogIn Code Unitl Log Out Button Is Clicked
    override fun onStart() {
        super.onStart()
         val currentUser = auth.currentUser
        if(signoutFlag==0) {
            if (currentUser != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }else{
            auth.signOut()
        }
    }


    private fun updateUi(user: FirebaseUser?)
    {
        val intent = Intent(this, MainActivity::class.java)
        Toast.makeText(this, "Welcome To TuneTribe, $usernameSigned", Toast.LENGTH_LONG).show()
        startActivity(intent)
        finish()
    }
    //End


    //Extract Username From Gmail
    private fun extractAndStoreUsername(email: String?) {
        email?.let {
            // Extract username from email (assuming email is in correct format)
            val parts = email.split("@")
            if (parts.size == 2) {
                username = parts[0]
            }
        }
    }

    private fun extractAndStoreUsernameInNormal(email: String?) {
        email?.let {
            emailSigned=email
            // Extract username from email (assuming email is in correct format)
            val parts = email.split("@")
            if (parts.size == 2) {
                usernameSigned = parts[0]
            }
        }
    }

    private fun storePaymentID(paymentID: String) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("PaymentID", paymentID)
        editor.apply()
    }

    private fun clearPaymentID() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        Liked_Songs.likedSongs.clear()
        editor.remove("PaymentID")
        editor.apply()
    }

    // Save user information to SharedPreferences
    private fun saveUserInfoToPrefs(username: String, userGmail: String) {
        val sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("email", userGmail)
        editor.apply()
    }

}

