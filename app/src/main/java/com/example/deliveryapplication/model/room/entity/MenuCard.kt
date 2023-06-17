package com.example.deliveryapplication.model.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "Menucard" , foreignKeys = [
        ForeignKey(entity= MenuData::class,
        parentColumns=["menuItemId"],childColumns = ["menuItem_Id"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE ),
        ForeignKey(entity= Card::class,
        parentColumns=["cardId"],childColumns = ["card_id"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE )
])
data class MenuCard(
    @PrimaryKey
    var menuItem_Id:Long,
    var card_id: Long,
)
