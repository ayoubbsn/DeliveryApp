package com.example.deliveryapplication.model


import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room

@Database(entities = [Card::class,MenuData::class,MenuCard::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardDao() : CardDao
    abstract fun menuCardDao() : MenuCardDao
    abstract fun menuDataDao() : MenuDataDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun buildDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(
                        context, AppDatabase::class.java,
                        "users_db"
                    ).allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }
}