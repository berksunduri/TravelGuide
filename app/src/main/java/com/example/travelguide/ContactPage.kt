package com.example.travelguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class ContactPage : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var menuButton: ImageButton
    private val ADMIN_USER_UUID = "bKwTZbnDMOW8nIhTICCJmoNNoBw2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_page)



        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById<NavigationView>(R.id.nav_view)
        val menu = navView.menu
        val createPostItem =  menu.findItem(R.id.nav_createpost)
        menuButton = findViewById(R.id.menu_button)


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
}