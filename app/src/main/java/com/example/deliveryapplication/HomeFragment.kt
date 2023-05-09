package com.example.deliveryapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HomeFragment : Fragment() {
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //view model
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)


        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = rootView.findViewById(R.id.restList) as RecyclerView
        recyclerView.setHasFixedSize(true)
        val mlayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = mlayoutManager

        val restList = viewModel.restaurantList
        recyclerView.adapter = RecyclerViewHomeAdapter(restList)

        (recyclerView.adapter as RecyclerViewHomeAdapter).setOnItemClickListener(object : RecyclerViewHomeAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Navigate to second fragment using NavController
                findNavController().navigate(R.id.action_homeFragment_to_menuFragment)
            }
        })

        return rootView
    }

}