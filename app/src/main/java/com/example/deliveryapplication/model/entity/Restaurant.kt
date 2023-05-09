package com.example.deliveryapplication.model.entity

data class Restaurant(
    val name: String,
    val description: String,
    val imageRes: Int,
    val price: Int,
    val rating: Double,
    val reviewNumber : Int,
    val longitude: Double? = 0.0,
    val latitude: Double? = 0.0,
    val location: String? = null,
    val cuisineType: String? = null,
    val contactPhone: Int? = null,
    val contactMail: String? = null,
    val socialMediaPages :String? = null
) {
}
