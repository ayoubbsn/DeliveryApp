package com.example.deliveryapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapplication.model.retrofit.entity.MenuItems


class RecyclerViewAdapterMenu(var data: List<MenuItems>) :
    RecyclerView.Adapter<RecyclerViewAdapterMenu.MyViewHolder>() {

    private var itemSelectionStates = Array(data.size) { ItemSelectionState() }
    private lateinit var selectionActionButton: Button
    private var itemSelectedListener: OnItemSelectedListener? = null

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        )
    }

    fun updateMenuItems(menuItems: List<MenuItems>) {
        this.data = menuItems
        this.itemSelectionStates = Array(data.size) { ItemSelectionState() }
        notifyDataSetChanged()
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position], itemSelectionStates[position].isSelected)
    }

    fun toggleSelection(position: Int) {
        itemSelectionStates[position].isSelected = !itemSelectionStates[position].isSelected
        notifyItemChanged(position)
        updateSelectionActionButtonVisibility()
    }

    fun getSelectedItems(): MutableList<MenuItems> {
        val selectedItems = mutableListOf<MenuItems>()
        for (i in data.indices) {
            if (itemSelectionStates[i].isSelected) {
                selectedItems.add(data[i])
            }
        }
        return selectedItems
    }

    fun setSelectionActionButton(button: Button) {
        selectionActionButton = button
        updateSelectionActionButtonVisibility()
    }

    fun setOnItemSelectedListener(listener: OnItemSelectedListener) {
        itemSelectedListener = listener
    }

    private fun updateSelectionActionButtonVisibility() {
        if (getSelectedItems().isEmpty()) {
            selectionActionButton.visibility = View.GONE
        } else {
            selectionActionButton.visibility = View.VISIBLE
            itemSelectedListener?.onItemSelected(getSelectedItems())
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.menuItemName)
        private val description = view.findViewById<TextView>(R.id.item_men_des)
        private val price = view.findViewById<TextView>(R.id.item_price)

        @SuppressLint("SetTextI18n")
        fun bind(item: MenuItems, isSelected: Boolean) {
            name.text = item.name
            description.text = item.description
            price.text = "${item.price} DA"
            itemView.isActivated = isSelected
            if (itemView.isActivated) {
                itemView.setBackgroundResource(R.drawable.selected)
            }else{
                itemView.setBackgroundResource(R.drawable.rounded)
            }

            itemView.setOnClickListener {
                toggleSelection(adapterPosition)
            }
        }
    }

    interface OnItemSelectedListener {
        fun onItemSelected(selectedItems: List<MenuItems>)
    }

    private class ItemSelectionState(var isSelected: Boolean = false)
}
