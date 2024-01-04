package com.example.travelguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUpPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var signupButton: Button
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        auth = FirebaseAuth.getInstance()

        name = findViewById(R.id.etName)
        email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etPassword)

        signupButton = findViewById(R.id.signButton)

        signupButton.setOnClickListener{
            if(checkAllField()) {
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener {
                    //if account created successfully
                    //is automatically signed in
                    if(it.isSuccessful) {
                        auth.signOut()
                        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                        backToLoginPage()
                    }
                    else {
                       Log.e("error:", it.exception.toString())
                    }
                }
            }
        }
    }

    private fun checkAllField(): Boolean {
        if(email.text.toString() == ""){
            Toast.makeText(this, "Please input E-mail", Toast.LENGTH_SHORT).show()
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            Toast.makeText(this, "Wrong E-mail Format", Toast.LENGTH_SHORT).show()
            return false
        }
        if(password.text.toString() == ""){
            Toast.makeText(this, "Please input Password", Toast.LENGTH_SHORT).show()
            return false
        }
        if(password.text.toString().length <= 8){
            Toast.makeText(this, "Password should be at least 8 characters", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
    fun backToLoginPage()
    {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
        finish()
    }

}