package com.example.travelguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class CategoriesPage : AppCompatActivity() {

    private lateinit var menuButton: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var historicalButton: RelativeLayout
    private lateinit var lodgingButton: RelativeLayout
    private lateinit var gastronomyButton: RelativeLayout
    private lateinit var shoppingButton: RelativeLayout
    private lateinit var culturalButton: RelativeLayout
    private lateinit var outdoorButton: RelativeLayout
    private lateinit var transportationButton: RelativeLayout
    private val ADMIN_USER_UUID = "bKwTZbnDMOW8nIhTICCJmoNNoBw2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories_page)

        drawerLayout = findViewById(R.id.drawer_layout)
        menuButton = findViewById(R.id.venuesmenu_button)
        navView = findViewById(R.id.nav_view)
        val menu = navView.menu
        val createPostItem =  menu.findItem(R.id.nav_createpost)

        historicalButton = findViewById(R.id.historicalsitesbutton)
        lodgingButton = findViewById(R.id.lodgingbutton)
        gastronomyButton = findViewById(R.id.gastronomybutton)
        shoppingButton = findViewById(R.id.shoppingbutton)
        culturalButton = findViewById(R.id.eventsbutton)
        outdoorButton = findViewById(R.id.outdoorbutton)
        transportationButton = findViewById(R.id.transportationbutton)


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

        historicalButton.setOnClickListener {
            IntentAdapter.openHistoricalSitesPage(this)
        }
        lodgingButton.setOnClickListener {
            IntentAdapter.openLodgingPage(this)
        }
        gastronomyButton.setOnClickListener {
            IntentAdapter.openGastronomyPage(this)
        }
        shoppingButton.setOnClickListener {
            IntentAdapter.openShoppingSpotsPage(this)
        }
        culturalButton.setOnClickListener {
            IntentAdapter.openCulturalEventsPage(this)
        }
        outdoorButton.setOnClickListener {
            IntentAdapter.openOutdoorActivitiesPage(this)
        }
        transportationButton.setOnClickListener {
            IntentAdapter.openTransportationPage(this)
        }
    }
}
//Accommodations
//Historical Sites
//Gastronomy
//Shopping Spots
//Cultural Events
//Outdoor Activities
//Transportation