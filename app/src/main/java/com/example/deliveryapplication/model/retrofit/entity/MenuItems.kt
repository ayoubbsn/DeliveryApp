package com.example.deliveryapplication.model.retrofit.entity

data class MenuItems(
    val id: Int,
    val restaurant_id : Int ,
    val name : String ,
    val description : String ,
    val price : Int ,
    val image : String
)
