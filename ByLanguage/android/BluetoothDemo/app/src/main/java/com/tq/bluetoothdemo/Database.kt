package com.tq.bluetoothdemo

import android.content.*
import androidx.room.Entity
import androidx.room.*
import androidx.lifecycle.*
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers.IO


@Entity(tableName="notice")
data class Notice(
    @PrimaryKey val id: Long?,
    val time: Long,
    val text: String
)


@Dao
interface NoticeDao {
    @Query("SELECT id,time,text FROM notice")
    fun getNotices(): LiveData<List<Notice>>

    @Insert
    fun insertNotice(notice: Notice)
}

@Database(entities=[Notice::class], version=1, exportSchema=false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noticeDao(): NoticeDao

    companion object {
        val name = "notice"
        lateinit var instance: AppDatabase
        
        fun initialize(context: Context) {
            instance = Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.name).build()
        }
    }
}

class Repository private constructor(private val database: AppDatabase) {

    fun getNotices() = database.noticeDao().getNotices()

    suspend fun insertNotice(notice: Notice) {
        withContext(IO) {
            database.noticeDao().insertNotice(notice);
        }
    }

    companion object {
        lateinit var instance: Repository
        fun initialize(database: AppDatabase) {
            instance = Repository(database)
        }
    }
}
