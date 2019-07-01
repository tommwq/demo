package com.tq.myapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Bar")
data class Bar(
    @PrimaryKey(autoGenerate=true)
    val id: Long = 0L
)
