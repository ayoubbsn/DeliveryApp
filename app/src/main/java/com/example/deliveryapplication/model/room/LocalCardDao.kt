package com.example.deliveryapplication.model.room

import androidx.room.*
import com.example.deliveryapplication.model.room.entity.CardItemL

@Dao
interface LocalCardDao {

    @Query("SELECT MAX(id) FROM cards")
    fun getLastSeqId() : Int


    @Query("SELECT * FROM cards")
    fun getAllCards() : List<CardItemL>

    @Insert
    fun addCards(vararg Cards: CardItemL)

    @Update
    fun updateCard(Card: CardItemL)

    @Delete
    fun deleteCard(Card: CardItemL)

    @Query("DELETE FROM cards WHERE id=:id_card ")
    fun deleteCardById(id_card: Int)


}