package com.example.travelguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginPage : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var forgot: TextView
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        email = findViewById(R.id.editEmail)
        password = findViewById(R.id.editPassword)
        loginButton = findViewById(R.id.loginButton)
        forgot = findViewById(R.id.forgotPassword)
        signupButton = findViewById(R.id.signupButton)

        signupButton.setOnClickListener { openSignupPage() }



    }
    fun openSignupPage()
    {
        val intent= Intent(this, SignUpPage::class.java)
        startActivity(intent)
    }
}