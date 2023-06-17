package com.example.deliveryapplication.model.retrofit

import com.example.deliveryapplication.model.retrofit.entity.MenuItems
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MenuItemAPI {

    @GET("/menuitem/restaurant/{id_restaurant}")
    suspend fun getMenuItems(@Path("id_restaurant") id: Int): Response<List<MenuItems>>


}