package com.tq.startup.tabbedactivity.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tq.startup.tabbedactivity.data.QuotationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Tab2ViewModel(private val repository: QuotationRepository) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Main + viewModelJob)

    val text = MutableLiveData<String>()
    init {
        text.value = "tab 2"
    }

    var author = ""
    var content = ""

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun commit() {
        viewModelScope.launch {
            repository.addQuotation(author, content)
        }
    }
}

