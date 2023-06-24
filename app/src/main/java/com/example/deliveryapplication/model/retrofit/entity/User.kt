package com.example.deliveryapplication.model.retrofit.entity

data class User(
    val id: Int ,
    val name: String,
    val email: String,
    val phone_number:String,
    val address: String,
    val password: String,
    val latitude: Float,
    val longitude: Float,
    val profile_picture: String,
    val registration_type: String,
    val social_media_id: String
)
