package com.example.deliveryapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText


class reviewsFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var reviewAdapter: RecyclerViewAdapterReviews
    var restaurantIdIns = 0
    var userId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //toolbar essentials
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        // Get reference to submit button
        val submitButton = view.findViewById<Button>(R.id.submitRev)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val reviewEditText = view.findViewById<TextInputEditText>(R.id.editTextTextMultiLine)

        val pref0 = context?.getSharedPreferences("idUser", Context.MODE_PRIVATE)
        userId = pref0?.getInt("idUser",0) ?: 0


        // Add click listener to submit button
        submitButton.setOnClickListener {
            if (userId != 0){
                val rating = ratingBar.rating
                val review = reviewEditText.text.toString()

                // Simple validation - check that review is not empty
                if (review.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter a review", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.submitReview(rating.toInt(), review, restaurantIdIns, userId)
                }
            }else{

            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_reviews, container, false)

        //view model
        viewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        // recycler view
        val recyclerView = rootView.findViewById(R.id.rcReview) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)

        reviewAdapter = RecyclerViewAdapterReviews(emptyList(), viewModel)

        // Setting adapter to recycler view
        recyclerView.adapter = reviewAdapter

        restaurantIdIns = arguments?.getInt("restaurantId") ?: 0

        viewModel.fetchRatingsForRestaurant(restaurantIdIns).observe(viewLifecycleOwner, Observer {it ->
            reviewAdapter.updateItems(it)
        })

        return rootView
    }

}