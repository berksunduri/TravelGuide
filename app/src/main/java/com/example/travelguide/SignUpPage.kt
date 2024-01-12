package com.example.travelguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.FirebaseDatabase

class SignUpPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var signupButton: Button
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var alreadylogin: TextView
    private lateinit var firebaseDatabase: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()

        name = findViewById(R.id.etName)
        email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etPassword)
        alreadylogin = findViewById(R.id.textView4)

        signupButton = findViewById(R.id.signButton)

        signupButton.setOnClickListener {
            if (checkAllField()) {
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val currentUserID = auth.currentUser?.uid
                            if (currentUserID != null) {
                                val usersRef = firebaseDatabase.reference.child("Users").child(currentUserID)
                                val userMap = HashMap<String, Any>()
                                userMap["Name"] = name.text.toString()
                                userMap["Email"] = email.text.toString()

                                // Generate a unique key for the user in the Realtime Database
                                val userKey = usersRef.push().key
                                if (userKey != null) {
                                    usersRef.child(userKey).updateChildren(userMap)
                                        .addOnCompleteListener { databaseTask ->
                                            if (databaseTask.isSuccessful) {
                                                auth.signOut()
                                                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                                                backToLoginPage()
                                            } else {
                                                Log.e("FirebaseDatabase", "Failed to store user data: ${databaseTask.exception}")
                                            }
                                        }
                                } else {
                                    Log.e("FirebaseDatabase", "Failed to generate user key")
                                }
                            }
                        } else {
                            if (task.exception is FirebaseAuthUserCollisionException) {
                                Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show()
                            } else {
                                Log.e("error:", task.exception.toString())
                                // Handle other exceptions if needed
                            }
                        }
                    }
            }
        }
        alreadylogin.setOnClickListener { backToLoginPage() }
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
        if(password.text.toString().length < 8){
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