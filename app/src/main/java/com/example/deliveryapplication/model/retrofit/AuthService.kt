package com.example.deliveryapplication.model.retrofit
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    data class RegisterRequest(
        val name: String,
        val email: String,
        val phone_number:String,
        val address: String,
        val password: String
    )

    data class RegisterResponse(
        val token: String,
        val id: Int
    )

    @POST("/auth/register")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    data class LoginRequest(
        val email: String,
        val password: String
    )

    data class LoginResponse(
        val token: String,
        val id:Int
    )

    @POST("/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

}
