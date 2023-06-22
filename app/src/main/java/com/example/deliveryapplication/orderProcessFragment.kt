package com.example.deliveryapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.deliveryapplication.model.entity.CardItem
import com.example.deliveryapplication.model.room.AppDatabase
import com.example.deliveryapplication.model.room.entity.CardItemL
import com.example.deliveryapplication.model.room.entity.MenuItemL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class orderProcessFragment : Fragment(), RecyclerViewOrderPAdapter.OnTotalPriceChangeListener {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var orderAdapter: RecyclerViewOrderPAdapter
    private lateinit var totalPriceTextView: TextView
    private val itemList = mutableListOf<CardItem>()
    private var restaurantIdIns = 0
    private var restaurantName = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_order_process, container, false)

        // RecyclerView
        val recyclerView = rootView.findViewById(R.id.cardItemsRcyView) as RecyclerView

        //total price init
        totalPriceTextView = rootView.findViewById(R.id.itemsTotal)

        // Initialize the adapter with an empty list
        orderAdapter = RecyclerViewOrderPAdapter(itemList, this)

        // Set up the RecyclerView
        val mLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = orderAdapter

        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        viewModel.selectedItems.observe(viewLifecycleOwner) { selectedItems ->
            itemList.clear()
            itemList.addAll(selectedItems.map { CardItem(it.id, it.name, it.price) })
            orderAdapter.notifyDataSetChanged()
        }

        //getting the current Restaurant ID
        restaurantIdIns = arguments?.getInt("restaurantId") ?: 0
        restaurantName = arguments?.getString("restaurantName") ?: ""


        val saveButton: Button = rootView.findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            val date = Date()
            val data = orderAdapter.getItems()
            lifecycleScope.launch(Dispatchers.IO) {
                val appDB = Room.databaseBuilder(
                    context?.applicationContext as Context,
                    AppDatabase::class.java,
                    "AppDB"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                val cardDao = appDB.cardDao()
                cardDao.addCards(
                    CardItemL(
                        date = date,
                        restaurantId = restaurantIdIns,
                        restaurantName = restaurantName,
                        totalPrice = orderAdapter.getTotalPrice()
                    )
                )
                val menuItemDao = appDB.menuItemDao()
                for (item in data) {
                        menuItemDao.addMenuDataItems(
                            MenuItemL(
                                item.id,
                                cardDao.getLastSeqId(),
                                restaurantIdIns,
                                item.name,
                                item.price,
                                item.quantity,
                                item.notes
                            )
                        )

                }

            }


        }

        return rootView
    }


    override fun onTotalPriceChange(totalPrice: Int) {
        totalPriceTextView.text = "$totalPrice DA"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar essentials
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }


    }
}
