package com.tq.databingdingdemo

import android.database.Observable
import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.tq.databingdingdemo.databinding.ActivityMainBinding


class User {
    var firstName = ObservableField<String>()
    var lastName = ObservableField<String>()
}

class Handler(val mainActivity: MainActivity) {
    fun onUserUpdate(view: View) {
        //val editText: EditText = mainActivity.findViewById(R.id.et_user) as EditText
        //mainActivity.user.firstName = editText.text.toString()
        //mainActivity.findViewById<TextView>(R.id.tv_name)
    }
}

class MainActivity : AppCompatActivity() {

    var handler: Handler = Handler(this)
    var user: User = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user.firstName.set("Test")
        user.lastName.set("Test");

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.user = user
        binding.handler = handler
    }
}
