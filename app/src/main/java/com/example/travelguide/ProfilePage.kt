package com.example.travelguide

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfilePage : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var birthdateEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var saveProfileButton: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        nameEditText = findViewById(R.id.etName)
        surnameEditText = findViewById(R.id.etSurname)
        birthdateEditText = findViewById(R.id.etBirthdate)
        usernameEditText = findViewById(R.id.etUsername)
        saveProfileButton = findViewById(R.id.btnSaveProfile)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()

        saveProfileButton.setOnClickListener {
            saveUserProfileData()
        }

        loadUserProfileData()
    }

    private fun loadUserProfileData() {
        val currentUserID = firebaseAuth.currentUser?.uid

        if (currentUserID != null) {
            val usersRef = firebaseDatabase.reference.child("Users").child(currentUserID)

            usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userData = snapshot.value as Map<String, String>?

                        // Retrieve user data from the snapshot
                        val name = userData?.get("Name")
                        val surname = userData?.get("Surname")
                        val birthdate = userData?.get("Birthdate")
                        val username = userData?.get("Username")

                        // Populate EditText fields with retrieved user data
                        nameEditText.setText(name)
                        surnameEditText.setText(surname)
                        birthdateEditText.setText(birthdate)
                        usernameEditText.setText(username)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle any errors that may occur during data retrieval
                    Log.e("FirebaseDatabase", "Error retrieving user data: $error")
                }
            })
        }
    }

    private fun saveUserProfileData() {
        val currentUserID = firebaseAuth.currentUser?.uid

        val name = nameEditText.text.toString().trim()
        val surname = surnameEditText.text.toString().trim()
        val birthdate = birthdateEditText.text.toString().trim()
        val username = usernameEditText.text.toString().trim()

        if (currentUserID != null) {
            val usersRef = firebaseDatabase.reference.child("Users").child(currentUserID)

            usersRef.child("Name").setValue(name)
            usersRef.child("Surname").setValue(surname)
            usersRef.child("Birthdate").setValue(birthdate)
            usersRef.child("Username").setValue(username)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Data saved successfully
                        // Add any desired UI feedback here (e.g., Toast or Snackbar)
                        Toast.makeText(this,"User info updated successfully!",Toast.LENGTH_SHORT).show()
                    } else {
                        // Failed to save data
                        // Handle the error, such as displaying an error message
                    }
                }
        }
    }
}
