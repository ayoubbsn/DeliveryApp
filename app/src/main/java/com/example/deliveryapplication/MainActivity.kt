package com.example.deliveryapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Context
import androidx.core.content.edit
import androidx.room.Room
import com.example.deliveryapplication.model.room.AppDatabase
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment_ =
            supportFragmentManager.findFragmentById(R.id.supFragContainer) as NavHostFragment
        val navController_ = navHostFragment_.navController
        bottomNavigationView.setupWithNavController(navController_)

        val pref = getSharedPreferences("Connect", Context.MODE_PRIVATE) ?: return
        pref.edit {
            putBoolean("connected", true)
        }

        val viewModelScope = CoroutineScope(Dispatchers.Main)

        viewModelScope.launch(Dispatchers.IO) {
            val db =
                Room.databaseBuilder(applicationContext, AppDatabase::class.java, "AppDatabase")
                    .fallbackToDestructiveMigration()
                    .build()
            println("data :" + db.menuDataDao().getMenuDataItemsAll())
        }

    }


}