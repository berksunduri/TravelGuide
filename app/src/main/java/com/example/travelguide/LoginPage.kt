package com.example.travelguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var forgot: TextView
    private lateinit var signupButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.editEmail)
        password = findViewById(R.id.editPassword)
        loginButton = findViewById(R.id.loginButton)
        forgot = findViewById(R.id.forgotPassword)
        signupButton = findViewById(R.id.signupButton)

        signupButton.setOnClickListener { IntentAdapter.openSignupPage(this) }
        forgot.setOnClickListener { IntentAdapter.openForgotPasswordPage(this) }

        loginButton.setOnClickListener {
            //if true (all fields correct)
            if(checkAllfield()){
                auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        //if successful
                        Toast.makeText(this, "Successfully logged in", Toast.LENGTH_SHORT).show()
                        //go to home page
                        IntentAdapter.openHomePage(this)
                    }
                    else{
                        //couldnt login
                        Log.e("error:", it.exception.toString())
                    }
                }
            }
        }
    }

    private fun checkAllfield(): Boolean {
        if(email.text.toString() == ""){
            Toast.makeText(this, "Please input E-mail", Toast.LENGTH_SHORT).show()
            return false
        }
        if(password.text.toString() == ""){
            Toast.makeText(this, "Please input Password", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}