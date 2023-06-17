package com.example.deliveryapplication.model.room

import androidx.room.*
import com.example.deliveryapplication.model.room.entity.MenuData

@Dao
interface MenuDataDao {


    @Query("select * from MenuDataItems")
    fun getMenuDataItemsAll(): List<MenuData>

    @Insert
    fun addMenuDataItems(vararg MenuDataItems: MenuData)

    @Update
    fun updateMenuDataItem(MenuDataItem: MenuData)

    @Delete
    fun deleteMenuDataItem(MenuDataItem: MenuData)
}