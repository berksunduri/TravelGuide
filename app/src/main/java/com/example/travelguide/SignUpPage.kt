package com.example.travelguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignUpPage : AppCompatActivity() {

    private lateinit var signupButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        signupButton = findViewById(R.id.signButton)

        signupButton.setOnClickListener{backToLoginPage()}
    }
    fun backToLoginPage()
    {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
    }

}