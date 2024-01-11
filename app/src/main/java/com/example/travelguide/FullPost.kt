package com.example.travelguide

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class FullPost : AppCompatActivity() {

    private lateinit var fullPostImageView: ImageView
    private lateinit var fullPostTitleTextView: TextView
    private lateinit var fullPostDescriptionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_post)
        fullPostImageView = findViewById(R.id.fullPostImageView)
        fullPostTitleTextView = findViewById(R.id.fullPostTitleTextView)
        fullPostDescriptionTextView = findViewById(R.id.fullPostDescriptionTextView)

        // Retrieve data from Intent
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val imageUrl = intent.getStringExtra("imageUrl")

        // Set data to views
        fullPostTitleTextView.text = title
        fullPostDescriptionTextView.text = description

        // Load image using Picasso
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .into(fullPostImageView)
    }
}