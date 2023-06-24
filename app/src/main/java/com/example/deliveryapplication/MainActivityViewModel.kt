package com.example.deliveryapplication


import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import androidx.room.Room
import com.example.deliveryapplication.model.retrofit.*

import com.example.deliveryapplication.model.retrofit.entity.MenuItems
import com.example.deliveryapplication.model.retrofit.entity.Rating
import com.example.deliveryapplication.model.retrofit.entity.Restaurants
import com.example.deliveryapplication.model.retrofit.entity.User
import com.example.deliveryapplication.model.room.AppDatabase
import com.example.deliveryapplication.model.room.LocalCardDao
import com.example.deliveryapplication.model.room.entity.CardItemL
import com.example.deliveryapplication.model.room.entity.MenuItemL
import kotlinx.coroutines.*
import retrofit2.create

class MainActivityViewModel : ViewModel() {


    private val restaurantAPI = RetrofitObject.getInstance().create(RestaurantAPI::class.java)
    private val menuItemAPI = RetrofitObject.getInstance().create(MenuItemAPI::class.java)
    private val userAPI = RetrofitObject.getInstance().create(UserAPI::class.java)
    private val ratingAPI  = RetrofitObject.getInstance().create(RatingAPI::class.java)

    val restaurantsLiveData = MutableLiveData<List<Restaurants>>()
    val menuItemsLiveData = MutableLiveData<List<MenuItems>>()
    val oneRestaurantLiveData: MutableLiveData<Restaurants?> = MutableLiveData()

    val cardsLiveData = MutableLiveData<List<CardItemL>>()
    val menuItemsLLiveData = MutableLiveData<List<MenuItemL>>()


    val selectedItems = MutableLiveData<List<MenuItems>>()

    var userLiveData = MutableLiveData<User>()


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

    fun getUser(id :Int){
        viewModelScope.launch(Dispatchers.IO) {
            val response = userAPI.getUserById(id)
            if (response.isSuccessful && response.body() != null) {
                userLiveData.postValue(response.body())
            }
        }
    }

    fun fetchRatingsForRestaurant(id_restaurant: Int): LiveData<List<Rating>> {
        val ratingsLiveData = MutableLiveData<List<Rating>>()
        viewModelScope.launch(Dispatchers.IO) {
            val response = ratingAPI.getRatingsOfRestaurant(id_restaurant)
            if (response.isSuccessful && response.body() != null) {
                ratingsLiveData.postValue(response.body())
            }
        }
        return ratingsLiveData
    }


    fun fetchALLCards(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val appDB = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "AppDB"
            )
                .fallbackToDestructiveMigration()
                .build()

            val cardAPI = appDB.cardDao()
            cardsLiveData.postValue(cardAPI.getAllCards())
        }
    }

    fun fetchMenuItemsL(idCard: Int, context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val appDB = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "AppDB"
            )
                .fallbackToDestructiveMigration()
                .build()

            val menuItemLAPI = appDB.menuItemDao()
            menuItemsLLiveData.postValue(menuItemLAPI.getMenuItemByCard(idCard))
        }
    }

    fun deleteCard(idCard: Int , context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val appDB = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "AppDB"
            )
                .fallbackToDestructiveMigration()
                .build()

            val menuItemLAPI = appDB.menuItemDao()
            val cardAPI = appDB.cardDao()
            cardAPI.deleteCardById(idCard)
            menuItemLAPI.deleteMenuItemById(idCard)
        }
    }


}