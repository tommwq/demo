package com.tq.startup.tabbedactivity.data

class QuotationRepository private constructor(private val quotationDao: QuotationDao) {

    fun getQuotations() = quotationDao.getQuotations()

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