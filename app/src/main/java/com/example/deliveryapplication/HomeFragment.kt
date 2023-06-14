package com.example.deliveryapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HomeFragment : Fragment() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: RecyclerViewHomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //view model
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.restList)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // Initialize adapter
        adapter = RecyclerViewHomeAdapter(emptyList())
        recyclerView.adapter = adapter

        // Fetch restaurants
        viewModel.restaurantsLiveData.observe(viewLifecycleOwner, Observer { restaurants ->
            // Update the restaurants in the adapter
            adapter.updateRestaurants(restaurants)
        })

        viewModel.fetchRestaurants()

        // click listener for redirection to the menu list
        adapter.setOnItemClickListener(object : RecyclerViewHomeAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Navigate to second fragment using NavController
                findNavController().navigate(R.id.action_homeFragment_to_menuFragment)
            }
        })

        return rootView
    }
}
