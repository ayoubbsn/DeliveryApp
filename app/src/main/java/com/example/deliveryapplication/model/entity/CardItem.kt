package com.example.deliveryapplication.model.entity

data class CardItem(
    val id : Int,
    val name : String,
    var price : Int = 0,
    var quantity : Int = 1,
    var notes : String = ""
)
