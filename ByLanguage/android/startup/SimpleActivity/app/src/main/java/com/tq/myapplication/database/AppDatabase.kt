package com.tq.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Bar::class/* TODO PUT_ENTITY_CLASSES_HERE */], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // TODO abstract fun getSomeDao(): SomeDao

    companion object {
        val DATABASE_NAME = "MYDATABASE"

        private var instance: AppDatabase? = null
        fun get(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
            }
        }
    }
}
