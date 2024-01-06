package com.example.travelguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var confirmButton: Button
    private lateinit var backtoLogin: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_page)


        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.editforgotEmail)
        confirmButton = findViewById(R.id.confirmButton)
        backtoLogin = findViewById(R.id.backtoLoginText)

        confirmButton.setOnClickListener{
            if(checkEmail()){
                auth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        //email is sent
                        Toast.makeText(this, "Email sent!", Toast.LENGTH_SHORT).show()
                        backToLoginPage()
                        finish()
                    }
                }
            }

        }

        backtoLogin.setOnClickListener {
            backToLoginPage()
        }
    }

    private fun checkEmail(): Boolean {
        if(email.text.toString() == ""){
            Toast.makeText(this, "Please input E-mail", Toast.LENGTH_SHORT).show()
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            Toast.makeText(this, "Wrong E-mail Format", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    private fun backToLoginPage()
    {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
        finish()
    }
}