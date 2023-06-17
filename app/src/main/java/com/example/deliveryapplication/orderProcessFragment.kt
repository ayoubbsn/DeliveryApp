package com.example.deliveryapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapplication.model.entity.CardItem

class orderProcessFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var orderAdapter: RecyclerViewOrderPAdapter
    private val itemList = mutableListOf<CardItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_order_process, container, false)

        // RecyclerView
        val recyclerView = rootView.findViewById(R.id.cardItemsRcyView) as RecyclerView

        // Initialize the adapter with an empty list
        orderAdapter = RecyclerViewOrderPAdapter(itemList)

        // Set up the RecyclerView
        val mLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = orderAdapter

        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        viewModel.selectedItems.observe(viewLifecycleOwner) { selectedItems ->
            itemList.clear()
            itemList.addAll(selectedItems.map { CardItem(it.id, it.name) })
            orderAdapter.notifyDataSetChanged()
        }

        return rootView
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
