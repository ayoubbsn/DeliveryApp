package com.example.deliveryapplication.model.retrofit

import com.example.deliveryapplication.model.retrofit.entity.Restaurants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantAPI {

    @GET("/restaurant")
    suspend fun getRestaurants() :  Response<List<Restaurants>>

    @GET("/restaurant/{id_restaurant}")
    suspend fun getRestaurantById(@Path("id_restaurant") id: Int) : Response<Restaurants>

}