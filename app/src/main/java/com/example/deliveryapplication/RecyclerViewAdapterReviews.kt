package com.example.deliveryapplication
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapplication.model.retrofit.entity.Rating
import com.example.deliveryapplication.model.retrofit.entity.User

class RecyclerViewAdapterReviews(private var reviewsList: List<Rating>, private val viewModel: MainActivityViewModel) : RecyclerView.Adapter<RecyclerViewAdapterReviews.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return ViewHolder(view, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = reviewsList[position]
        holder.bind(review)
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }

    inner class ViewHolder(itemView: View, private val viewModel: MainActivityViewModel) : RecyclerView.ViewHolder(itemView) {
        private val revTitle: TextView = itemView.findViewById(R.id.revTitle)
        private val ratingRev: TextView = itemView.findViewById(R.id.ratingRev)
        private val contextRv: TextView = itemView.findViewById(R.id.contentRv)

        fun bind(review: Rating) {
            viewModel.getUserById(review.user_id)
            viewModel.userLiveData.observeForever(Observer<User> { user ->
                revTitle.text = user.name
                ratingRev.text = review.rating.toString()+" Stars "
                contextRv.text = review.review
            })
        }
    }

    fun updateItems(reviewsList: List<Rating>){
        this.reviewsList = reviewsList
        notifyDataSetChanged()
    }
}
