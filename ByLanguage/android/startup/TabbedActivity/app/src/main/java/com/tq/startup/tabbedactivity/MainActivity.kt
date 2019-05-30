package com.tq.startup.tabbedactivity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tq.startup.tabbedactivity.databinding.ActivityMainBinding
import com.tq.startup.tabbedactivity.adapter.TabPageAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewPager = binding.viewPager
        val tabs = binding.tabs
        val fab = binding.fab

        val sectionsPagerAdapter =
            TabPageAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}