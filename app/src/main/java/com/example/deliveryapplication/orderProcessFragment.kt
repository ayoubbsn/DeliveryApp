package com.example.deliveryapplication

import android.annotation.SuppressLint
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.deliveryapplication.model.entity.CardItem
import com.example.deliveryapplication.model.retrofit.entity.Order
import com.example.deliveryapplication.model.retrofit.entity.OrderItem
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
    private var prevFrag = 0
    private var cardId = 0


    @SuppressLint("SetTextI18n")
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

        //bundle init
        restaurantIdIns = arguments?.getInt("restaurantId") ?: 0
        restaurantName = arguments?.getString("restaurantName") ?: ""
        prevFrag = arguments?.getInt("fragment") ?: 0
        cardId = arguments?.getInt("cardId") ?: 0

        //view model init
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        //recycler view items init
        if (prevFrag == 1) {
            viewModel.selectedItems.observe(viewLifecycleOwner) { selectedItems ->
                itemList.clear()
                itemList.addAll(selectedItems.map { CardItem(it.id, it.name, it.price) })
                orderAdapter.notifyDataSetChanged()
            }
        } else if (prevFrag == 2) {
            viewModel.fetchMenuItemsL(cardId, context as Context)
            viewModel.menuItemsLLiveData.observe(viewLifecycleOwner) { menuItemsL ->
                itemList.clear()
                itemList.addAll(menuItemsL.map {
                    CardItem(
                        it.id,
                        it.name,
                        it.price,
                        it.quantity,
                        it.notes
                    )
                })
                orderAdapter.notifyDataSetChanged()
            }
        }


        val saveButton: Button = rootView.findViewById(R.id.saveButton)
        if (prevFrag == 2) saveButton.text = "Update"
        saveButton.setOnClickListener {
            if (prevFrag == 1) handleInsert()
            else if (prevFrag == 2) {
                deleteCard(cardId)
                handleInsert()
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
        val validateButton: Button = view.findViewById(R.id.validateButton)
        validateButton.setOnClickListener {
            val pref0 = context?.getSharedPreferences("idUser", Context.MODE_PRIVATE)
            val userId = pref0?.getInt("idUser", 0) ?: 0
            val order = Order(
                userId = userId,
                restaurantId = restaurantIdIns,
                deliveryAddress = "",
                deliveryNotes = "",
                orderStatus = "Pending",
                createdAt = "",
                updatedAt = ""
            )

            viewModel.addOrder(order).observe(viewLifecycleOwner, Observer { orderId ->
                orderId?.let {
                    val orderItems = orderAdapter.getItems().map { item ->
                        OrderItem(
                            orderId = it,
                            menuItemId = item.id,
                            quantity = item.quantity,
                            specialInstructions = item.notes
                        )
                    }

                    for (orderItem in orderItems) {
                        viewModel.addOrderItem(orderItem)
                    }


                }
            })
        }
    }


    fun handleInsert() {
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

    fun deleteCard(idCard: Int) {
        viewModel.deleteCard(idCard, context?.applicationContext as Context)
    }

}
