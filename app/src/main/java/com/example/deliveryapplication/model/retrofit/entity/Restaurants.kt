package com.example.deliveryapplication.model.retrofit.entity

data class Restaurants(
    val id: Int,
    val name: String,
    val description: String,
    val logo: String,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val cuisine_type: String,
    val contact_phone: String,
    val contact_email: String,
    val social_media_pages: String
)
