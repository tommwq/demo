package com.tq.startup.tabbedactivity.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class Tab2ViewModel : ViewModel() {
    val text = MutableLiveData<String>()
    init {
        text.value = "tab 2"
    }
}
