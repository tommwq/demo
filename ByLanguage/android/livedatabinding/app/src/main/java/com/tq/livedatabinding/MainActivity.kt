package com.tq.livedatabinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tq.livedatabinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.authenticationViewModel = ViewModelProviders.of(this).get(AuthenticationViewModel::class.java)
        binding.lifecycleOwner = this
        binding.getAuthenticationViewModel()?.authenticationInformation?.observe(this, object: Observer<AuthenticationInformation>{
            override fun onChanged(authenticationInformation: AuthenticationInformation) {
                Log.d("TEST", authenticationInformation.username)
            }
        })
    }
}
