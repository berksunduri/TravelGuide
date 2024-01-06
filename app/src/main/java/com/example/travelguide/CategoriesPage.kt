package com.example.travelguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class CategoriesPage : AppCompatActivity() {

    private lateinit var menuButton: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories_page)

        drawerLayout = findViewById(R.id.drawer_layout)
        menuButton = findViewById(R.id.venuesmenu_button)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.open, R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Handle Home Page click
                    openHomePage()
                }
                R.id.nav_categories -> {
                    // Handle Venues Page Click
                    openVenuesPage()
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
    private fun openVenuesPage()
    {
        val intent = Intent(this, CategoriesPage::class.java)
        startActivity(intent)
    }
    private fun openHomePage()
    {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
    fun openHistoricalSitesPage()
    {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
    fun openAccomodationsPage()
    {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
    fun openGastronomyPage()
    {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
    fun openShoppingSpotsPage()
    {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
    fun openCulturalEventsPage()
    {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
    fun openOutdoorActivitesPage()
    {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
    fun openTransportationPage()
    {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
}
//Accommodations
//Historical Sites
//Gastronomy
//Shopping Spots
//Cultural Events
//Outdoor Activities
//Transportation