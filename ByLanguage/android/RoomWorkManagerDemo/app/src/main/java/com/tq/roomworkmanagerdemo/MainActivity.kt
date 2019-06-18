package com.tq.roomworkmanagerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Context
import androidx.lifecycle.*
import androidx.room.Entity
import androidx.room.Room
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Database
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.work.*
import com.tq.roomworkmanagerdemo.databinding.ActivityMainBinding
import androidx.databinding.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Dispatchers.IO

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppDatabase.init(this)
        Repository.init(AppDatabase.instance.userDao())

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val model = ViewModelProviders.of(this).get(UserViewModel::class.java)
        binding.model = model
        model.users.observe(this, Observer {
            it.forEach {
                android.util.Log.d("TEST", "user: ${it.id} ${it.name}")
            }
        })
    }
}


@Entity(tableName="user")
data class User (
    @PrimaryKey(autoGenerate=true) @ColumnInfo(name="id") var id: Long = 0,
    var name: String = ""
)

@Dao
interface UserDao {
    @Query("select id,name from user")
    fun getUsers(): LiveData<List<User>>
    
    @Insert
    fun insertUser(user: User): Long
}

@Database(entities=[User::class], version=1, exportSchema=false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        lateinit var instance: AppDatabase
        fun init(context: Context) {
            instance = Room.databaseBuilder(context, AppDatabase::class.java, "test").build()
        }
    }
}

class Repository private constructor(private val userDao: UserDao) {

    fun insertUser(user: User): Long {
        android.util.Log.d("TEST", "before insert user $user")
        val result =  userDao.insertUser(user)
        android.util.Log.d("TEST", "after insert user $user")
        return result
    }
    
    fun getUsers() = userDao.getUsers()
    
    companion object {
        lateinit var instance: Repository
        fun init(userDao: UserDao) {
            instance = Repository(userDao)
        }
    }
}

class UserViewModel: ViewModel() {

    var id = ObservableField<String>()
    var name = MutableLiveData<String>()
    var users = Repository.instance.getUsers()

    init {
        id.set("?")
        name.value = ""
    }
    
    fun createUser(v: View) {
        // withContext(IO) {
        CoroutineScope(IO).launch {
            val userId = Repository.instance.insertUser(User(0, name.value!!))
            id.set(userId.toString())
            android.util.Log.d("TEST", "id = $userId")
        }
    }
}

