package com.example.deliveryapplication.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "MenuDataItems")
data class MenuData(
    @PrimaryKey
    var menuItemId:Long,
    var name: String,
    var description: String,
    var price: Int
)
