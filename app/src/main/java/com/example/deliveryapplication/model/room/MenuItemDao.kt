package com.example.deliveryapplication.model.room

import androidx.room.*
import com.example.deliveryapplication.model.room.entity.MenuItemL

@Dao
interface MenuItemDao {




    @Query("select * from MenuDataItems")
    fun getMenuDataItemsAll(): List<MenuItemL>

    @Query("SELECT * FROM MenuDataItems WHERE cardId = :id_card")
    fun getMenuItemByCard(id_card: Int): List<MenuItemL>

    @Insert
    fun addMenuDataItems(vararg menuItemLItems: MenuItemL)

    @Update
    fun updateMenuDataItem(menuItemLItem: MenuItemL)

    @Delete
    fun deleteMenuDataItem(menuItemLItem: MenuItemL)

    @Query("DELETE FROM MenuDataItems WHERE cardId=:id_card")
    fun deleteMenuItemById(id_card: Int)
}