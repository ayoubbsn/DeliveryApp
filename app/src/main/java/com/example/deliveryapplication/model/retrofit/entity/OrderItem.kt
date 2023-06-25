package com.example.deliveryapplication.model.retrofit.entity
data class OrderItem(
    val orderId: Int,
    val menuItemId: Int,
    val quantity: Int,
    val specialInstructions: String?
)