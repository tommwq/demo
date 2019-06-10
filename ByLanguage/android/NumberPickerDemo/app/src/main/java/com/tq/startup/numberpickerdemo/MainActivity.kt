package com.tq.startup.numberpickerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

	val numberPicker = findViewById<NumberPicker>(R.id.number_picker)
	val numbers = arrayOf("1", "2", "3", "4", "5")
	numberPicker.displayedValues = numbers
	numberPicker.minValue = 1
	numberPicker.maxValue = 5
	numberPicker.value = 1
	numberPicker.wrapSelectorWheel = true
	numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
	numberPicker.setOnValueChangedListener({
	    _: NumberPicker, oldValue: Int, newValue: Int ->
	    Log.d("TEST", "old: $oldValue, new: $newValue")
	})
    }
}
