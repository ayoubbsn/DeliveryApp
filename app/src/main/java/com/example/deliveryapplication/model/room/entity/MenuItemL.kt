package com.example.deliveryapplication.model.room.entity

import androidx.room.Entity

@Entity(
    tableName = "MenuDataItems",
    primaryKeys = ["id", "cardId"]
)
data class MenuItemL(
    val id: Int,
    val cardId: Int,
    val restaurantId: Int,
    val name : String,
    var price : Int = 0,
    var quantity : Int = 1,
    var notes : String = ""
)
