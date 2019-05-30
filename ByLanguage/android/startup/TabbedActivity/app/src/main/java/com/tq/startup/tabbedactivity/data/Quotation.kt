package com.tq.startup.tabbedactivity.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotation")
data class Quotation(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long?,
    val author: String,
    val content: String
)