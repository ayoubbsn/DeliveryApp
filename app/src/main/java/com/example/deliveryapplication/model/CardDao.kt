package com.example.deliveryapplication.model

import androidx.room.*
import java.sql.Date

@Dao
interface CardDao {


    @Insert
    fun addCards(vararg Cards: Card)

    @Update
    fun updateCard(Card: Card)

    @Delete
    fun deleteCard(Card: Card)
}