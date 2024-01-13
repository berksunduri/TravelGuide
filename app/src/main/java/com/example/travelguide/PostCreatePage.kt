package com.example.travelguide

import com.example.travelguide.Post
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class PostCreatePage : AppCompatActivity() {
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var storageReference: StorageReference

    private lateinit var categoryDropdown: Spinner
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var imageView: ImageView
    private lateinit var uploadImageButton: Button
    private lateinit var createPostButton: Button

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var menuButton: ImageButton
    private val ADMIN_USER_UUID = "bKwTZbnDMOW8nIhTICCJmoNNoBw2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_create_page)

        firebaseDatabase = FirebaseDatabase.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        categoryDropdown = findViewById(R.id.categoryDropdown)
        titleEditText = findViewById(R.id.titleEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        imageView = findViewById(R.id.imageView)
        uploadImageButton = findViewById(R.id.uploadImageButton)
        createPostButton = findViewById(R.id.createPostButton)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById<NavigationView>(R.id.nav_view)
        val menu = navView.menu
        val createPostItem =  menu.findItem(R.id.nav_createpost)
        menuButton = findViewById(R.id.menu_button)

        fetchCategories()

        uploadImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        createPostButton.setOnClickListener {
            val selectedCategory = categoryDropdown.selectedItem.toString()
            val title = titleEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()

            if (selectedCategory.isNotEmpty() && title.isNotEmpty() && description.isNotEmpty()) {
                val postId = firebaseDatabase.reference.child("Posts").child(selectedCategory).push().key

                postId?.let { id ->
                    val postReference = firebaseDatabase.reference.child("Posts").child(selectedCategory).child(id)
                    val post = Post(title, description, "") // Initialize imageUrl as an empty string

                    postReference.setValue(post)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Post created successfully!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Failed to create post. Please try again.", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val filePath = data.data
            val ref = storageReference.child("images/${System.currentTimeMillis()}.jpg")

            ref.putFile(filePath!!)
                .addOnSuccessListener { taskSnapshot ->
                    // Image upload successful, get the download URL
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        // Set the download URL in the 'imageView' field
                        imageView.setImageURI(uri)

                        // Update the imageUrl in the database
                        updateImageUrlToDatabase(uri.toString())
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun fetchCategories() {
        val categoryList = mutableListOf<String>()

        val categoriesRef = firebaseDatabase.reference.child("Posts")

        categoriesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (categorySnapshot in dataSnapshot.children) {
                    val categoryName = categorySnapshot.key
                    categoryName?.let {
                        categoryList.add(categoryName)
                    }
                }

                val adapter = ArrayAdapter(this@PostCreatePage, android.R.layout.simple_spinner_item, categoryList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                categoryDropdown.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@PostCreatePage, "Failed to fetch categories", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateImageUrlToDatabase(imageUrl: String) {
        // Get the selected category and post ID
        val selectedCategory = categoryDropdown.selectedItem.toString()
        val postId = firebaseDatabase.reference.child("Posts").child(selectedCategory).push().key

        postId?.let { id ->
            // Update the imageUrl in the database
            val postReference = firebaseDatabase.reference.child("Posts").child(selectedCategory).child(id)
            postReference.child("imageUrl").setValue(imageUrl)
        }
    }
    companion object {
        private const val PICK_IMAGE_REQUEST = 71
    }
}
