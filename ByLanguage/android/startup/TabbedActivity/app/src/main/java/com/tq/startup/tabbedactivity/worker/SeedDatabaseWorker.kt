package com.tq.startup.tabbedactivity.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.tq.startup.tabbedactivity.data.AppDatabase
import com.tq.startup.tabbedactivity.data.Quotation

class SeedDatabaseWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters) {

    override fun doWork(): Result {
        return try {
            Log.d("TEST", "insert")
            val database = AppDatabase.getInstance(applicationContext)
            database.quotationDao().insert(Quotation(null, "鲁迅", "晚睡的人没对象！"))
            database.quotationDao().insert(Quotation(null, "鲁迅", "说谁说的都行，别说是我说的。"))
            database.quotationDao().insert(Quotation(null, "鲁迅", "我即使是死了，钉在棺材里了，也要在墓地里，用这腐朽的声带喊出：我没说过这句话。"))
            database.quotationDao().insert(Quotation(null, "鲁迅", "网上95%的名人名言都是瞎掰，包括这句。"))
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }
}