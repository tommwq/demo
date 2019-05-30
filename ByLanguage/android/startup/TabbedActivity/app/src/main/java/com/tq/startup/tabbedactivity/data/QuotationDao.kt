package com.tq.startup.tabbedactivity.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuotationDao {
    @Query("SELECT id,author,content FROM quotation")
    fun getQuotations(): LiveData<List<Quotation>>

    @Insert
    fun insert(quotation: Quotation)
}