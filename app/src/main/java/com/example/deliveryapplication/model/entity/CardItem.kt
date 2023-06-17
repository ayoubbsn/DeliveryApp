package com.example.deliveryapplication.model.entity

data class CardItem(
    val id : Int,
    val name : String,
    var quantity : Int = 0,
    var notes : String = ""
)
