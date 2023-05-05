package com.example.deliveryapplication.model

import androidx.room.*

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