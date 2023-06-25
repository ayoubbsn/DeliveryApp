package com.example.deliveryapplication.model.retrofit.entity

data class RatingSend(
    val user_id: Int,
    val restaurant_id :Int,
    val rating: Int,
    val review: String,
    val created_at: String
)