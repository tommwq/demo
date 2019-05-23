package com.tq.livedatabinding

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class TextViewModel() : ViewModel() {

    var text = MutableLiveData<TextModel>()
    var content = "hello"
    set(value) {
        field = value
        val x = TextModel()
        x.content = value
        text.postValue(x)
    }
}