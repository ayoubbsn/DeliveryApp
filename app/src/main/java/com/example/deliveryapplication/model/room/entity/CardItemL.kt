package com.example.deliveryapplication.model.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "cards")
data class CardItemL(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Date,
    val restaurantId: Int,
    val restaurantName: String,
    var totalPrice: Int
)
