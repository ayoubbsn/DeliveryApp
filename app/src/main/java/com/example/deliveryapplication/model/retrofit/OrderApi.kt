package com.example.deliveryapplication.api

import com.example.deliveryapplication.model.retrofit.entity.Order
import com.example.deliveryapplication.model.retrofit.entity.OrderItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApi {
    @POST("orders")
    suspend fun createOrder(@Body order: Order): Response<Order>

    @POST("orderItems")
    suspend fun createOrderItem(@Body orderItem: OrderItem): Response<OrderItem>
}