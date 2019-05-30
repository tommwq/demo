package com.tq.startup.tabbedactivity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tq.startup.tabbedactivity.data.QuotationRepository

class Tab1ViewModelFactory(private val repository: QuotationRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        Tab1ViewModel(repository) as T
}