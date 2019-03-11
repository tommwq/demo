package com.tq.bottomnavigationviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.fragment)
        val navView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navView.setupWithNavController(navController)
    }
}
