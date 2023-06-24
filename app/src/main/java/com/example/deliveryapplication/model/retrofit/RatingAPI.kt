package com.example.deliveryapplication.model.retrofit

import com.example.deliveryapplication.model.retrofit.entity.Rating
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RatingAPI {

    @GET("/rating/restaurant/{id_restaurant}")
    suspend fun getRatingsOfRestaurant(@Path("id_restaurant") id_restaurant: Int): Response<List<Rating>>


    @POST("/rating/{id_user}/{id_restaurant}")
    suspend fun newRating(
        @Path("id_user") id_user: Int,
        @Path("id_restaurant") id_restaurant: Int,
        @Body rating: Rating
    ) : Call<Void>
}