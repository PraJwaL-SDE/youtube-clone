package com.example.youtubeclone

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class StartingScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String // Not initialized here, make sure to initialize before using
    private lateinit var password: String // Not initialized here, make sure to initialize before using
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting_screen)
        // Initialize Firebase Auth
        auth = Firebase.auth

        // Initiating Google Authentication
        googleAuth()

        // Initiating Firebase Authentication
        // This function requires email and password, make sure to initialize them before calling this function
//        firebaseAuth()
    }

    // Function to handle Google authentication
    private fun googleAuth() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this, gso)

        val acct = GoogleSignIn.getLastSignedInAccount(this)
//        if (acct != null) {
//            navigateToSecondActivity()
//        }

        val signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

//    // Function to handle onActivityResult for Google Sign-in
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1000) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//
//            try {
//                task.getResult(ApiException::class.java)
//                navigateToSecondActivity()
//            } catch (e: ApiException) {
//                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    // Function to navigate to the SecondActivity
    private fun navigateToSecondActivity() {
        finish()
        val intent = Intent(this, Navigation_Home::class.java)
        startActivity(intent)
    }

    // Function to handle Firebase authentication
    private fun firebaseAuth(email: String, password: String) {
        // Create a new Firebase user with the provided email and password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    // You can perform additional actions here after successful sign-in
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    // Function to handle onActivityResult for Google Sign-in
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                // Use the email from Google sign-in to create a new Firebase user
                account?.email?.let { email ->
                    firebaseAuth(email, email) // Use the email as both email and password
                }
                navigateToSecondActivity()
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Google sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // onStart lifecycle method
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
//            reload()
            navigateToSecondActivity()
        }
    }
}