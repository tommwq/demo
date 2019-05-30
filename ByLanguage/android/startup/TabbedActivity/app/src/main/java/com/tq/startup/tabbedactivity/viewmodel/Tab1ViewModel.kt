package com.tq.startup.tabbedactivity.viewmodel

import androidx.lifecycle.*
import com.tq.startup.tabbedactivity.data.Quotation
import com.tq.startup.tabbedactivity.data.QuotationRepository


class Tab1ViewModel(private val repository: QuotationRepository) : ViewModel() {

    val quotations = MediatorLiveData<List<Quotation>>()

    init {
        quotations.addSource(repository.getQuotations(), quotations::setValue)
    }
}