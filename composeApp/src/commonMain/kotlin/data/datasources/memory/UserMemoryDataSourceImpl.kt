package data.datasources.memory

import data.api.memory.UserDao
import data.entities.UserEntity
import data.repositories.UserMemoryDataSource
import kotlinx.coroutines.flow.Flow

class UserMemoryDataSourceImpl(private val userDao: UserDao) : UserMemoryDataSource {

    override fun getUsers(): Flow<List<UserEntity>> = userDao.getAll()

    override suspend fun getNumUsers(): Int {
        return userDao.getNumUsers()
    }

    override suspend fun addUsers(users: List<UserEntity>) {
        userDao.insertAll(users)
    }

    override suspend fun deleteUser(user: UserEntity): Boolean {
        return userDao.update(user.copy(isActive = false)) == 1
    }

    override fun getUser(email: String): Flow<UserEntity?> {
        return userDao.getUser(email)
    }
}
