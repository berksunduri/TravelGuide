package com.example.travelguide

import PostsAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GastronomyPage : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var postsAdapter: PostsAdapter
    private lateinit var databaseReference: DatabaseReference
    private var gastronomy = "Gastronomy"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gastronomy_page)

        recyclerView = findViewById(R.id.recyclerViewGastronomy)
        recyclerView.layoutManager = LinearLayoutManager(this)
        postsAdapter = PostsAdapter(gastronomy)
        recyclerView.adapter = postsAdapter

        databaseReference = FirebaseDatabase.getInstance().reference.child("Posts").child(gastronomy)
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


    }
}