package com.tq.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BarDao {

    @Query("SELECT id FROM Bar")
    fun queryAll(): LiveData<List<Bar>>
 
    @Query("SELECT id FROM Bar WHERE id=:id")
    fun query(id: Long): LiveData<Bar>
 
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun insert(entity: Bar): Long

    @Delete
    fun delete(entity: Bar)
}
