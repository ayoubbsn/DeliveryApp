package com.example.deliveryapplication.model.retrofit.entity

data class Order(
    val id: Int =0,
    val userId: Int,
    val restaurantId: Int,
    val deliveryAddress: String,
    val deliveryNotes: String?,
    val orderStatus: String,
    val createdAt: String,
    val updatedAt: String
)