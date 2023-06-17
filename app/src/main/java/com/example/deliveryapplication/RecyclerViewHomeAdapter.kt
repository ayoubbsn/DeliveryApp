package com.example.deliveryapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deliveryapplication.model.entity.Restaurant
import com.example.deliveryapplication.model.retrofit.RetrofitObject
import com.example.deliveryapplication.model.retrofit.entity.Restaurants

class RecyclerViewHomeAdapter(var data: List<Restaurants>) :
    RecyclerView.Adapter<RecyclerViewHomeAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.restaurant_item, parent, false)
        )
    }

    override fun getItemCount() = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            id = data[position].id
            name.text = data[position].name
            price.text = "250 DA"

            // rating handling
            val avgRating = 5.0
            ratingHandler(avgRating, rating)


            // logo handling
            Glide.with(holder.itemView)
                .load(RetrofitObject.baseUrl + data[position].logo)
                .into(logo)

            reviewNb.text = "( 10 )"

            holder.itemView.setOnClickListener {
                listener?.onItemClick(position, id)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, id: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var id: Int = 0

        val name = view.findViewById(R.id.nameRes) as TextView
        val price = view.findViewById(R.id.priceTxt) as TextView//
        val logo = view.findViewById(R.id.mainPic) as ImageView
        val rating = view.findViewById(R.id.ratingTxt) as TextView
        val reviewNb = view.findViewById(R.id.revNumTxt) as TextView

    }

    fun updateRestaurants(restaurants: List<Restaurants>) {
        this.data = restaurants
        notifyDataSetChanged()
    }

    //just a logic class
    @SuppressLint("SetTextI18n")
    fun ratingHandler(avgRating: Double, rating: TextView) {
        if (avgRating >= 4) {
            rating.text = "$avgRating superb"
        } else if (avgRating > 3) {
            rating.text = "$avgRating good"
        } else if (avgRating > 2) {
            rating.text = "$avgRating acceptable"
        } else {
            rating.text = "$avgRating bad"
        }
    }
}