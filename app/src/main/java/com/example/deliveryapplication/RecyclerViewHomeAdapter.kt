package com.example.deliveryapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapplication.model.Restaurant

class RecyclerViewHomeAdapter(val data: List<Restaurant>) :
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
            nom.text = data[position].name
            price.text = data[position].price.toInt().toString() + " DA"

            val avgRating = data[position].rating
            if (avgRating >= 4) {
                rating.text = "$avgRating superb"
            } else if (avgRating > 3) {
                rating.text = "$avgRating good"
            } else if (avgRating > 2) {
                rating.text = "$avgRating acceptable"
            } else {
                rating.text = "$avgRating bad"
            }

            image.setImageResource(data[position].imageRes)
            reviewNb.text = "(" +data[position].reviewNumber+")"

            holder.itemView.setOnClickListener {
                listener?.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }



    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nom = view.findViewById(R.id.nameRes) as TextView
        val price = view.findViewById(R.id.priceTxt) as TextView
        val image = view.findViewById(R.id.mainPic) as ImageView
        val rating = view.findViewById(R.id.ratingTxt) as TextView
        val reviewNb = view.findViewById(R.id.revNumTxt) as TextView
        var magnitude = 0.0
        var longitude = 0.0
    }
}