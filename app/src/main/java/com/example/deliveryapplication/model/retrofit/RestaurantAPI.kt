package com.example.deliveryapplication.model.retrofit

import com.example.deliveryapplication.model.retrofit.entity.Restaurants
import retrofit2.Response
import retrofit2.http.GET

interface RestaurantAPI {

    @GET("/restaurant")
    suspend fun getRestaurants() :  Response<List<Restaurants>>

}