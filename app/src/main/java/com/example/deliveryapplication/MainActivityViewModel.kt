package com.example.deliveryapplication


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.deliveryapplication.model.retrofit.MenuItemAPI
import com.example.deliveryapplication.model.retrofit.RestaurantAPI
import com.example.deliveryapplication.model.retrofit.RetrofitObject
import com.example.deliveryapplication.model.retrofit.entity.MenuItems
import com.example.deliveryapplication.model.retrofit.entity.Restaurants
import kotlinx.coroutines.*

class MainActivityViewModel : ViewModel() {

    private val restaurantAPI = RetrofitObject.getInstance().create(RestaurantAPI::class.java)
    private val menuItemAPI = RetrofitObject.getInstance().create(MenuItemAPI::class.java)
    val restaurantsLiveData = MutableLiveData<List<Restaurants>>()
    val menuItemsLiveData = MutableLiveData<List<MenuItems>>()
    val oneRestaurantLiveData: MutableLiveData<Restaurants?> = MutableLiveData()

    val selectedItems = MutableLiveData<List<MenuItems>>()


    fun fetchRestItemsData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = menuItemAPI.getMenuItems(id)
            if (response.isSuccessful && response.body() != null) {
                menuItemsLiveData.postValue(response.body())
            }
        }
    }

    fun fetchRestaurants() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = restaurantAPI.getRestaurants()
            if (response.isSuccessful && response.body() != null) {
                restaurantsLiveData.postValue(response.body())
            }
        }
    }
    fun fetchRestaurant(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = restaurantAPI.getRestaurantById(id)
            if (response.isSuccessful && response.body() != null) {
                oneRestaurantLiveData.postValue(response.body())
            }
        }
    }



}