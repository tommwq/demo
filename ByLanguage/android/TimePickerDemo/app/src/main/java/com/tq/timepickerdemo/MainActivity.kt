package com.tq.timepickerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timePicker1 = findViewById<TimePicker>(R.id.time_picker1)
    }
}
