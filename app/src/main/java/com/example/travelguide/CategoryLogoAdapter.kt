import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelguide.LogoItem
import com.example.travelguide.R

class CategoryLogoAdapter(private val logoItems: List<LogoItem>) :
    RecyclerView.Adapter<CategoryLogoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_category_logo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val logoItem = logoItems[position]
        holder.bind(logoItem)
    }

    override fun getItemCount(): Int = logoItems.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val logoImageView: ImageView = itemView.findViewById(R.id.logo)
        private val logoTextView: TextView = itemView.findViewById(R.id.logoText)

        fun bind(logoItem: LogoItem) {
            logoImageView.setImageResource(logoItem.logoResId)
            logoTextView.text = logoItem.text
        }
    }
}

