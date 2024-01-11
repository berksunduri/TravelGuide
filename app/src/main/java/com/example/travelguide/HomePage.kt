package com.example.travelguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class HomePage : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var menuButton: ImageButton
    private lateinit var imageSlider: ViewPager2
    private lateinit var imageList: List<Int> // List of image resources (drawable IDs)
    private val ADMIN_USER_UUID = "bKwTZbnDMOW8nIhTICCJmoNNoBw2"

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
        imageSlider = findViewById(R.id.image_slider)

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

        // Prepare a list of image resources (drawable IDs)
       // imageList = listOf(R.drawable.imageslide1, R.drawable.imageslide2, R.drawable.imageslide3, R.drawable.imageslide4)

        // Set up the ViewPager2 adapter
        //val adapter = ImageSliderAdapter(imageList)
        //imageSlider.adapter = adapter
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
}