package com.example.deliveryapplication.model

import androidx.room.*


@Dao
interface MenuCardDao {

   // @Query("select * from Menucard where card_id=:menuCard")
    //fun getMenuCardByCardId(menuCard: MenuCard): List<MenuCard>

    @Insert
    fun addMenuDataCardItems(vararg menuDataItems: MenuCard)


    @Update
    fun updateMenuDataCardItem(MenuDataItem: MenuCard)

    @Delete
    fun deleteMenuDataCardItem(MenuDataItem: MenuCard)
}