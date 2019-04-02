package com.tq.navigationdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.*
import androidx.navigation.ui.*
import com.tq.navigationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.garden_nav_fragment)
        binding.navigationView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        //        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        return true
    }

    // override fun onBackPressed() {
    //     if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
    //         drawerLayout.closeDrawer(GravityCompat.START)
    //     } else {
    //         super.onBackPressed()
    //     }
    // }
}
