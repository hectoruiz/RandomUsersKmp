package data.api.memory

import androidx.room.Database
import androidx.room.RoomDatabase
import data.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(), DB {

    abstract fun userDao(): UserDao
    override fun clearAllTables() {}
}

//temporal hack
interface DB {
    fun clearAllTables() {}
}
