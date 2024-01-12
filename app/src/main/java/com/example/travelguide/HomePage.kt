package com.example.travelguide

import CategoryLogoAdapter
import PostsAdapter
import android.app.Dialog
import AllPostsAdapter
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomePage : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var menuButton: ImageButton
    private val ADMIN_USER_UUID = "bKwTZbnDMOW8nIhTICCJmoNNoBw2"
    private lateinit var preferences: SharedPreferences
    private lateinit var dialog: Dialog
    private lateinit var searchView: SearchView

    private lateinit var postsAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById<NavigationView>(R.id.nav_view)
        val menu = navView.menu
        val createPostItem =  menu.findItem(R.id.nav_createpost)
        menuButton = findViewById(R.id.menu_button)
        searchView = findViewById(R.id.searchView)

        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val userAgreed = preferences.getBoolean("userAgreed", false)

        // Assuming you have a Firebase Database reference
        var databaseReference = FirebaseDatabase.getInstance().reference


        val currentUserUUID = getCurrentUserUUID()
// Update the agreement status in the database
        if (currentUserUUID != null) {

        }


        if (currentUserUUID != null) {

            databaseReference.child("usersAgreementStatus").child(currentUserUUID)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val agreementAccepted = dataSnapshot.getValue(Boolean::class.java) ?: false

                        if (!agreementAccepted) {
                            showAgreementDialog()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle error
                    }
                })
        }


        if (currentUserUUID != null) {
            val userPreferences = getSharedPreferences("MyPrefs_$currentUserUUID", MODE_PRIVATE)
            val userAgreed = userPreferences.getBoolean("userAgreed", false)

            if (!userAgreed) {
                showAgreementDialog()
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
                    // Handle Home Page click
                    IntentAdapter.openHomePage(this)
                }
                R.id.nav_categories -> {
                    // Handle Venues Page Click
                    IntentAdapter.openCategoriesPage(this)
                }
                R.id.nav_profile -> {
                    // Handle Venues Page Click
                    IntentAdapter.openProfilePage(this)
                }
                R.id.nav_createpost -> {
                    if (createPostItem.isVisible) {
                        IntentAdapter.openCreatePostPage(this)
                    } else {
                        // If the current user is not the admin, show a message or handle accordingly
                        Toast.makeText(this, "You don't have permission to access this feature", Toast.LENGTH_SHORT).show()
                    }
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

        var recyclerView: RecyclerView = findViewById(R.id.category_logo_recycler)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

// Replace YOUR_LOGO_RES_IDS with the actual list of logo resource IDs
        val logoItems = listOf(
            LogoItem(R.drawable.historicalsites, "Historical Sites"),
            LogoItem(R.drawable.accomodations, "Lodging"),
            LogoItem(R.drawable.gastronomy, "Gastronomy"),
            LogoItem(R.drawable.shoppingspots, "Shopping Spots"),
            LogoItem(R.drawable.events, "Cultural Events"),
            LogoItem(R.drawable.outdoor, "Outdoor Activites"),
            LogoItem(R.drawable.transportation, "Transportation")
        )

        val adapter = CategoryLogoAdapter(logoItems)

        recyclerView.adapter = adapter


        recyclerView = findViewById(R.id.postsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        postsAdapter = PostsAdapter("Historical Sites")
        recyclerView.adapter = postsAdapter

        databaseReference = FirebaseDatabase.getInstance().reference.child("Posts").child("Historical Sites")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val postsList = ArrayList<DataSnapshot>() // Change to DataSnapshot list
                for (postSnapshot in dataSnapshot.children) {
                    postsList.add(postSnapshot) // Add DataSnapshot objects directly
                }
                postsAdapter.setPosts(postsList)
            }



            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter posts based on the search query
                postsAdapter.filter.filter(newText)
                return true
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Set a default starting point
        val defaultLocation = LatLng(42.209006, 20.740557) // Example: Shadirvan coordinates
        val zoomLevel = 12.0f // Set the desired zoom level

        // Move the camera to the default location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, zoomLevel))
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAgreementDialog() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_agreement)

        val checkBoxAgree: CheckBox = dialog.findViewById(R.id.checkBoxAgree)
        val buttonAccept: Button = dialog.findViewById(R.id.buttonAccept)

        checkBoxAgree.setOnCheckedChangeListener { _, isChecked ->
            buttonAccept.isEnabled = isChecked
        }

        buttonAccept.setOnClickListener {
            // Update the agreement status for the current user
            val currentUserUUID = getCurrentUserUUID()
            currentUserUUID?.let {
                var databaseReference = FirebaseDatabase.getInstance().reference
                databaseReference.child("usersAgreementStatus").child(currentUserUUID).setValue(true)
                val userPreferences = getSharedPreferences("MyPrefs_$it", MODE_PRIVATE)
                userPreferences.edit().putBoolean("userAgreed", true).apply()
            }

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun getCurrentUserUUID(): String? {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser?.uid
    }


}