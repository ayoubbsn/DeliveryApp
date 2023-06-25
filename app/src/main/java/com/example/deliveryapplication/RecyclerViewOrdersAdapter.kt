package com.example.deliveryapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapplication.model.room.entity.CardItemL

class RecyclerViewOrdersAdapter(
    private var items: List<CardItemL>
) : RecyclerView.Adapter<RecyclerViewOrdersAdapter.ViewHolder>() {

    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewOrdersAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = items[position]
        holder.bindData(order)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setClickListener(listener: ItemClickListener) {
        clickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val orderRestName: TextView = itemView.findViewById(R.id.restName)
        private val date: TextView = itemView.findViewById(R.id.date)
        private val price: TextView = itemView.findViewById(R.id.totalPriceOrder)

        init {
            itemView.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bindData(order: CardItemL) {
            orderRestName.text = order.restaurantName
            date.text = order.date.toString().substring(0,16)
            price.text = "price : ${order.totalPrice} DA"
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            clickListener?.onItemClick(view, position)
        }
    }

    fun updateData(newItems: List<CardItemL>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    fun getData(): List<CardItemL> {
        return this.items
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }



}
