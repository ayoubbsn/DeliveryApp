package com.example.deliveryapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.deliveryapplication.model.retrofit.RetrofitObject


class menuFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var selectionActionButton: Button
    private lateinit var menuAdapter: RecyclerViewAdapterMenu
    private var restaurantId = 0
    private var restNameSend = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //view model init
        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        val rootView = inflater.inflate(R.layout.fragment_menu, container, false)

        val recyclerView = rootView.findViewById(R.id.menuList) as RecyclerView
        recyclerView.setHasFixedSize(true)

        val mlayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = mlayoutManager

        menuAdapter = RecyclerViewAdapterMenu(emptyList())
        recyclerView.adapter = menuAdapter

        //initialization of the observer
        viewModel.menuItemsLiveData.observe(viewLifecycleOwner, Observer { menuitems ->
            // Update the restaurants in the adapter
            menuAdapter.updateMenuItems(menuitems)
        })

        //getting the id and calling the view model for restaurant update
        restaurantId = arguments?.getInt("restaurantId") ?: 0
        viewModel.fetchRestItemsData(restaurantId)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //toolbar essentials
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        //restaurant information initialization
        restInfoInit(view)

        selectionActionButton = view.findViewById(R.id.button)
        selectionActionButton.setOnClickListener {
            val pref = context?.getSharedPreferences("Connect", Context.MODE_PRIVATE)
            val connected = pref?.getBoolean("connected", false)
            if (connected == true) {
                val selectedItemsList = menuAdapter.getSelectedItems()
                viewModel.selectedItems.value = selectedItemsList
                val bundle = Bundle()
                bundle.putInt("restaurantId",restaurantId)
                bundle.putString("restaurantName",restNameSend)
                findNavController().navigate(R.id.action_menuFragment_to_orderProcessFragment,bundle)
            }
        }


        menuAdapter.setSelectionActionButton(selectionActionButton)
    }


    fun restInfoInit(view: View) {
        val image = view.findViewById<ImageView>(R.id.restPic)
        viewModel.oneRestaurantLiveData.observe(viewLifecycleOwner, Observer { restaurant ->
            if (restaurant != null) {
                Glide.with(view)
                    .load(RetrofitObject.baseUrl + restaurant.logo)
                    .into(image)
                val name = view.findViewById(R.id.nameRes) as TextView
                val description = view.findViewById(R.id.descRes) as TextView
                val cuisineType = view.findViewById(R.id.typeRes) as TextView

                restNameSend = restaurant.name
                name.text = restaurant.name
                description.text = restaurant.description
                cuisineType.text = restaurant.cuisine_type
            }
        })

        viewModel.fetchRestaurant(restaurantId)

    }
}
