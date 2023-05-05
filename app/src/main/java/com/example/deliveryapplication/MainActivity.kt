package com.example.deliveryapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Context
import androidx.core.content.edit
import com.example.deliveryapplication.model.AppDatabase
import com.example.deliveryapplication.model.MenuData
import com.example.deliveryapplication.model.MenuItem


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment_ = supportFragmentManager.findFragmentById(R.id.supFragContainer) as NavHostFragment
        val navController_ = navHostFragment_.navController
        bottomNavigationView.setupWithNavController(navController_)

        val pref = getSharedPreferences("Connect",Context.MODE_PRIVATE) ?: return
        pref.edit{
            putBoolean("connected", true)
        }

        val appDatabase = AppDatabase.buildDatabase(this)
        println("data :" + appDatabase?.menuDataDao()?.getMenuDataItemsAll())

    }


}