package com.example.deliveryapplication

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapplication.model.entity.CardItem

class RecyclerViewOrderPAdapter(
    private val items: MutableList<CardItem>,
    private val listener: OnTotalPriceChangeListener
) :
    RecyclerView.Adapter<RecyclerViewOrderPAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemNotes: EditText = itemView.findViewById(R.id.itemNotes)
        val itemQuantity: TextView = itemView.findViewById(R.id.itemQuantity)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        private val increaseButton: Button = itemView.findViewById(R.id.incrementButton)
        private val decreaseButton: Button = itemView.findViewById(R.id.decrementButton)

        init {
            listener.onTotalPriceChange(getTotalPrice())

            increaseButton.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                items[position].apply {
                    quantity++
                    itemQuantity.text = quantity.toString()
                    itemPrice.text = formatPrice(quantity * price)
                    listener.onTotalPriceChange(getTotalPrice())
                }
            }

            decreaseButton.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                items[position].apply {
                    if (quantity > 1) {
                        quantity--
                        itemQuantity.text = quantity.toString()
                        itemPrice.text = formatPrice(quantity * price)
                    } else {
                        items.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, items.size)
                    }
                    listener.onTotalPriceChange(getTotalPrice())
                }
            }


            itemQuantity.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        s?.toString()?.toIntOrNull()?.let {
                            items[pos].quantity = it
                        }
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            itemNotes.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        items[pos].notes = s.toString()
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.orderpitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = item.name
        holder.itemQuantity.text = item.quantity.toString()
        holder.itemNotes.setText(item.notes)
        holder.itemPrice.text = formatPrice(item.price * item.quantity)
    }

    override fun getItemCount() = items.size

    fun getItems(): MutableList<CardItem> {
        return this.items
    }


    private fun formatPrice(price: Int) = "$price DA"

    fun getTotalPrice(): Int {
        var totalPrice = 0
        for (item in items) {
            totalPrice += item.quantity * item.price
        }
        return totalPrice
    }

    interface OnTotalPriceChangeListener {
        fun onTotalPriceChange(totalPrice: Int)
    }

}
