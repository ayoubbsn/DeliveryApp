package com.example.deliveryapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cards")
data class Card(
    @PrimaryKey
    var cardId:Long,
    var date: String,
)
