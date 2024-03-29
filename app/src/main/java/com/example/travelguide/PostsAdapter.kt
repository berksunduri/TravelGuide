import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelguide.FullPost
import com.example.travelguide.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class PostsAdapter(private val category: String) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>(), Filterable {

    private var postsList: List<DataSnapshot> = ArrayList()
    private var filteredPostsList: List<DataSnapshot> = ArrayList()
    private val filter: Filter = PostFilter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postSnapshot = filteredPostsList[position]
        holder.bind(postSnapshot)
    }

    override fun getItemCount(): Int {
        return filteredPostsList.size
    }

    fun setPosts(posts: List<DataSnapshot>) {
        this.postsList = posts
        this.filteredPostsList = posts
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

    override fun getFilter(): Filter {
        return filter
    }

    inner class PostFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val query = constraint?.toString()?.toLowerCase()
            val results = FilterResults()

            if (query.isNullOrBlank()) {
                results.values = postsList
            } else {
                val filteredList = postsList.filter { postSnapshot ->
                    val title =
                        postSnapshot.child("title").getValue(String::class.java) ?: ""
                    title.toLowerCase().contains(query)
                }
                results.values = filteredList
            }

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredPostsList = results?.values as List<DataSnapshot>
            notifyDataSetChanged()
        }
    }
}
