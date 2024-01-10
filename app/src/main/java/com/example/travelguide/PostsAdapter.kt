import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelguide.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class PostsAdapter(private val category: String) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    private var postsList: List<DataSnapshot> = ArrayList()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postSnapshot = postsList[position]
        holder.bind(postSnapshot)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    fun setPosts(posts: List<DataSnapshot>) {
        this.postsList = posts
        notifyDataSetChanged()
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewPost: ImageView = itemView.findViewById(R.id.imageViewPost)
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val buttonViewPost: Button = itemView.findViewById(R.id.buttonViewPost)

        fun bind(postSnapshot: DataSnapshot) {
            textViewTitle.text = postSnapshot.child("title").getValue(String::class.java)

            // Load image using Picasso library
            val imageUrl = postSnapshot.child("imageUrl").getValue(String::class.java)

                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageViewPost)


            buttonViewPost.setOnClickListener {
                // Handle button click to view full post
                // You can implement code here to navigate to the full post page
                // Example: Start a new activity to display the full post
            }
        }
    }
}
