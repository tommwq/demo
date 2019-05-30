package com.tq.startup.tabbedactivity.viewmodel

import androidx.lifecycle.*
import com.tq.startup.tabbedactivity.data.Quotation
import com.tq.startup.tabbedactivity.data.QuotationRepository

class Tab1ViewModelFactory(private val repository: QuotationRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        Tab1ViewModel(repository) as T
}


class Tab1ViewModel(private val repository: QuotationRepository) : ViewModel() {

    val quotations = MediatorLiveData<List<Quotation>>()

    init {
        quotations.addSource(repository.getQuotations(), quotations::setValue)
    }
}