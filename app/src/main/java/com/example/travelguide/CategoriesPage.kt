package com.example.travelguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories_page)

        drawerLayout = findViewById(R.id.drawer_layout)
        menuButton = findViewById(R.id.venuesmenu_button)
        navView = findViewById(R.id.nav_view)

        historicalButton = findViewById(R.id.historicalsitesbutton)
        lodgingButton = findViewById(R.id.lodgingbutton)
        gastronomyButton = findViewById(R.id.gastronomybutton)
        shoppingButton = findViewById(R.id.shoppingbutton)
        culturalButton = findViewById(R.id.eventsbutton)
        outdoorButton = findViewById(R.id.outdoorbutton)
        transportationButton = findViewById(R.id.transportationbutton)


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

        historicalButton.setOnClickListener {
            openHistoricalSitesPage()
        }
        lodgingButton.setOnClickListener {
            openLodgingPage()
        }
        gastronomyButton.setOnClickListener {
            openGastronomyPage()
        }
        shoppingButton.setOnClickListener {
            openShoppingSpotsPage()
        }
        culturalButton.setOnClickListener {
            openCulturalEventsPage()
        }
        outdoorButton.setOnClickListener {
            openOutdoorActivitesPage()
        }
        transportationButton.setOnClickListener {
            openTransportationPage()
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
    private fun openHistoricalSitesPage()
    {
        val intent = Intent(this, HistoricalSitesPage::class.java)
        startActivity(intent)
    }
    private fun openLodgingPage()
    {
        val intent = Intent(this, LodgingPage::class.java)
        startActivity(intent)
    }
    private fun openGastronomyPage()
    {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
    private fun openShoppingSpotsPage()
    {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
    private fun openCulturalEventsPage()
    {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
    private fun openOutdoorActivitesPage()
    {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
    private fun openTransportationPage()
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