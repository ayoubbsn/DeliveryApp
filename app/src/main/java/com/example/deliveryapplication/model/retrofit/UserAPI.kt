package com.example.deliveryapplication.model.retrofit

import com.example.deliveryapplication.model.retrofit.entity.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPI {

    @GET("/user/{id_user}")
    suspend fun getUserById(@Path("id_user") id: Int) : Response<User>



}