package com.tq.livedatabinding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthenticationViewModel() : ViewModel() {

    var authenticationInformation = MutableLiveData<AuthenticationInformation>()

    var username = "Alan"
        set(value) {
            field = value
            authenticationInformation.postValue(AuthenticationInformation(username, encryptPassword(password)))
        }

    var password = ""
        set(value) {
            field = value
            authenticationInformation.postValue(AuthenticationInformation(username, encryptPassword(password)))
        }

    private fun encryptPassword(originalPassword: String): String {
        // ...
        return "******"
    }
}