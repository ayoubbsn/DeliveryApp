package com.example.deliveryapplication

import android.content.Context
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


class ordersFragment : Fragment() , RecyclerViewOrdersAdapter.ItemClickListener {

    private lateinit var orderAdapter : RecyclerViewOrdersAdapter
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_orders, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)


        val recyclerView = rootView.findViewById<RecyclerView>(R.id.savedOrderRc)

        recyclerView.layoutManager  = LinearLayoutManager(activity)
        orderAdapter = RecyclerViewOrdersAdapter(emptyList())
        orderAdapter.setClickListener(this)
        recyclerView.adapter = orderAdapter

        viewModel.cardsLiveData.observe(viewLifecycleOwner , Observer { cards ->
            orderAdapter.updateData(cards)
        })


        viewModel.fetchALLCards(context as Context)



        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onItemClick(view: View, position: Int) {
        val data = orderAdapter.getData()
        val bundle = Bundle()
        bundle.putInt("fragment",2)
        bundle.putInt("cardId",data[position].id)
        bundle.putString("restaurantName",data[position].restaurantName)

        findNavController().navigate(R.id.action_ordersFragment_to_orderProcessFragment,bundle)
    }


}