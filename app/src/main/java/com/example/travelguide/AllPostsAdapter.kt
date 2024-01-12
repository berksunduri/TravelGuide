import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelguide.FullPost
import com.example.travelguide.R
import com.google.firebase.database.DataSnapshot
import com.squareup.picasso.Picasso

class AllPostsAdapter : RecyclerView.Adapter<AllPostsAdapter.PostViewHolder>() {

    private var postsList: List<DataSnapshot> = ArrayList()

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
            // Retrieve post details
            val title = postSnapshot.child("title").getValue(String::class.java)
            val description = postSnapshot.child("description").getValue(String::class.java)
            val imageUrl = postSnapshot.child("imageUrl").getValue(String::class.java)

            // Check if imageUrl is not empty or null before loading with Picasso
            if (!imageUrl.isNullOrEmpty()) {
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageViewPost)
            } else {
                // Handle the case when imageUrl is empty or null
                // You can set a default image or hide the ImageView
                imageViewPost.setImageResource(R.drawable.placeholder_image)
            }

            textViewTitle.text = title

            buttonViewPost.setOnClickListener {
                val intent = Intent(itemView.context, FullPost::class.java)
                intent.putExtra("title", title)
                intent.putExtra("description", description)
                intent.putExtra("imageUrl", imageUrl)
                itemView.context.startActivity(intent)
            }
        }
    }
}
