package com.tq.startup.tabbedactivity.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.tq.startup.tabbedactivity.worker.SeedDatabaseWorker

@Database(entities = [Quotation::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun quotationDao(): QuotationDao

    companion object {

        val DATABASE_NAME = "demo"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        WorkManager.getInstance().enqueue(request)
                        Log.d("TEST", "enqueue task")
                    }
                }).build()
        }
    }
}