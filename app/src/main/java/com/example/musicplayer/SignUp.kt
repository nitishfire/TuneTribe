package com.example.musicplayer

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.WindowManager
import com.example.musicplayer.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.musicplayer.fragment.HomeFragment
import com.example.musicplayer.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database

class SignUp : AppCompatActivity() {

     private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    //Google Authentication Code SET-1
    //Start
       private lateinit var auth: FirebaseAuth
       private lateinit var googleSignInClient:GoogleSignInClient
    //End


       //Email And Password Sign Up Set-1
       //Start
        private lateinit var password:String
        private lateinit var email:String
        private lateinit var database:DatabaseReference
       //End


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.imageButton.setOnClickListener{
            val intent = Intent(this,Welcome_Screen::class.java)
            startActivity(intent)
        }
        binding.button6.setOnClickListener{
            val intent = Intent(this,LogIn::class.java)
            startActivity(intent)
        }

        //Google Authentication Code Set-2
        //Start
        auth = Firebase.auth
         val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        googleSignInClient=GoogleSignIn.getClient(this, gso)

        binding.button.setOnClickListener {
            val signInClient=googleSignInClient.signInIntent
            launcher.launch(signInClient)
        }
    //End


        //Email And Password Sign Up Set-2
        //Start
            database=Firebase.database.reference
            binding.button5.setOnClickListener{
                email=binding.editTextText.text.toString()
                password=binding.editTextTextPassword.text.toString().trim()

                if(email.isEmpty() || password.isBlank())
                {
                    Toast.makeText(this, "Please Fill All The Details", Toast.LENGTH_LONG).show()
                }
                else{
                    createAccount(email,password)
                }
            }
        //End


        supportActionBar?.hide()

        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }


    //Google Authentication Code SET-3
    //Start
    @SuppressLint("SuspiciousIndentation")
    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
          result ->
          if (result.resultCode == Activity.RESULT_OK)
          {
              val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if(task.isSuccessful)
                {
                    val account:GoogleSignInAccount?=task.result
                     val credential=GoogleAuthProvider.getCredential(account?.idToken, null)

                    auth.signInWithCredential(credential).addOnCompleteListener{
                          if(it.isSuccessful)
                         {
                             Toast.makeText(this, "Welcome To TuneTribe", Toast.LENGTH_LONG).show()
                             //Redirect To Another Page If Sign Up Is Completed
                             startActivity(Intent(this, LogIn::class.java))
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


    //Email And Password Sign Up Set 3
    //Start
        private fun createAccount(email: String, password: String)
        {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                task ->

                if(task.isSuccessful){
                    saveUserData()
                    Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Welcome To TuneTribe", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LogIn::class.java))
                    finish()
                }
                else
                {
                    Toast.makeText(this, "Account Creation Failed, Please Try Google Sign Up Or Try Again" , Toast.LENGTH_SHORT).show()
                    Log.d("Account", "createAccount: Failure", task.exception)
                }
            }
        }

        private fun saveUserData(){
            email=binding.editTextText.text.toString()
            password=binding.editTextTextPassword.text.toString().trim()


            val user = UserModel(email, password)
            val userID = FirebaseAuth.getInstance().currentUser!!.uid
            //Sava Data To Database Firebase
            database.child("user").child(userID).setValue(user)
        }


    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (HomeFragment.signoutFlag == 0) {
            if (currentUser != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }else{
            auth.signOut()
        }
    }
    //End

}


