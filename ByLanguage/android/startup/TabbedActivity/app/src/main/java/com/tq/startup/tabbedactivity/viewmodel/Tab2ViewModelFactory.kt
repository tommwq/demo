package com.tq.startup.tabbedactivity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tq.startup.tabbedactivity.data.QuotationRepository

class Tab2ViewModelFactory(private val repository: QuotationRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        Tab2ViewModel(repository) as T
}