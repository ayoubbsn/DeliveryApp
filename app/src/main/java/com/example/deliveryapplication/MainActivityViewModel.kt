package com.example.deliveryapplication


import android.content.Context
import androidx.lifecycle.*
import androidx.room.Room
import com.example.deliveryapplication.api.OrderApi
import com.example.deliveryapplication.model.retrofit.*
import com.example.deliveryapplication.model.retrofit.entity.*

import com.example.deliveryapplication.model.room.AppDatabase
import com.example.deliveryapplication.model.room.entity.CardItemL
import com.example.deliveryapplication.model.room.entity.MenuItemL
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.create
import java.time.Instant

class MainActivityViewModel : ViewModel() {


    private val restaurantAPI = RetrofitObject.getInstance().create(RestaurantAPI::class.java)
    private val menuItemAPI = RetrofitObject.getInstance().create(MenuItemAPI::class.java)
    private val userAPI = RetrofitObject.getInstance().create(UserAPI::class.java)
    private val ratingAPI = RetrofitObject.getInstance().create(RatingAPI::class.java)
    private val orderApi = RetrofitObject.getInstance().create(OrderApi::class.java)

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

    fun getUserById(id: Int) {
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

    fun submitReview(rating: Int, review: String, idRestaurant: Int, idUser: Int) {
        val newRating = RatingSend(idUser, idRestaurant ,rating, review, Instant.now().toString())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ratingAPI.newRating(idUser, idRestaurant, newRating)
                if (response.isSuccessful) {
                    println("sent")
                } else {
                    println("didn't send")
                }
            } catch (e: Exception) {
                println(e)
            }
        }
    }
    fun addOrder(order: Order): LiveData<Int?> {
        val liveData = MutableLiveData<Int?>()

        viewModelScope.launch {
            val response = orderApi.createOrder(order)
            if (response.isSuccessful) {
                liveData.value = response.body()?.id
                println("order sent")
            } else {
                // Optionally handle the error here and post null to liveData
                liveData.value = null
            }
        }

        return liveData
    }


    fun addOrderItem(orderItem: OrderItem) {
        viewModelScope.launch {
            val response = orderApi.createOrderItem(orderItem)
            if (response.isSuccessful) {
                // handle success case
            } else {
                // handle error case
            }
        }
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

    fun fetchMenuItemsL(idCard: Int, context: Context) {
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

    fun deleteCard(idCard: Int, context: Context) {
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