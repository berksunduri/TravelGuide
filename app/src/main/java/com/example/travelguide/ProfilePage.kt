package com.example.travelguide

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
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

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var menuButton: ImageButton
    private val ADMIN_USER_UUID = "bKwTZbnDMOW8nIhTICCJmoNNoBw2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById<NavigationView>(R.id.nav_view)
        val menu = navView.menu
        val createPostItem =  menu.findItem(R.id.nav_createpost)
        menuButton = findViewById(R.id.menu_button)

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

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null && currentUser.uid == ADMIN_USER_UUID) {
            createPostItem.isVisible = true
        } else {
            createPostItem.isVisible = false
        }



        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.open, R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    IntentAdapter.openHomePage(this)
                }
                R.id.nav_categories -> {
                    IntentAdapter.openCategoriesPage(this)
                }
                R.id.nav_profile -> {
                    IntentAdapter.openProfilePage(this)
                }
                R.id.nav_createpost -> {
                    if (createPostItem.isVisible) {
                        IntentAdapter.openCreatePostPage(this)
                    } else {
                        Toast.makeText(this, "You don't have permission to access this feature", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.nav_contact -> {
                    IntentAdapter.openContactPage(this)

                }
                R.id.nav_help -> {
                    IntentAdapter.openFAQPage(this)
                }
                // Handle other menu items similarly
            }
            true
        }

        menuButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
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
