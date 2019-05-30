package com.tq.startup.tabbedactivity.data

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class QuotationRepository private constructor(private val quotationDao: QuotationDao) {

    fun getQuotations() = quotationDao.getQuotations()

    suspend fun addQuotation(author: String, content: String) {
        withContext(IO) {
            quotationDao.insert(Quotation(null, author, content))
        }
    }

    companion object {
        @Volatile
        private var instance: QuotationRepository? = null

        fun getInstance(quotationDao: QuotationDao) = instance ?: synchronized(this) {
            instance ?: QuotationRepository(quotationDao).also {
                instance = it
            }
        }
    }
}