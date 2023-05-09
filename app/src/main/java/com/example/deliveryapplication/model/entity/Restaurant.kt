package com.example.deliveryapplication.model.entity

data class Restaurant(
    val name: String,
    val description: String,
    val imageRes: Int,
    val price: Int,
    val rating: Double,
    val reviewNumber : Int,
    val longitude: Double? = 0.0,
    val magnitude: Double? = 0.0
) {
}
