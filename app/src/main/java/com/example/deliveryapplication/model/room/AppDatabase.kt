package com.example.deliveryapplication.model.room


import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import androidx.room.TypeConverters
import com.example.deliveryapplication.model.room.entity.CardItemL
import com.example.deliveryapplication.model.room.entity.MenuItemL

@Database(entities = [CardItemL::class, MenuItemL::class], version = 8)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardDao() : LocalCardDao
   abstract fun menuItemDao() : MenuItemDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun buildDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(
                        context, AppDatabase::class.java,
                        "AppDatabase"
                    ).allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }
}