package com.example.deliveryapplication.model.room

import androidx.room.*

@Dao
interface CardDao {


    @Query("SELECT MAX(cardId) FROM cards")
    fun getLastSeqId() : Long

    @Insert
    fun addCards(vararg Cards: Card)

    @Update
    fun updateCard(Card: Card)

    @Delete
    fun deleteCard(Card: Card)
}