package data.repositories

import data.entities.toEntity
import data.entities.toModel
import data.models.toModel
import domain.models.UserModel
import domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userMemoryDataSource: UserMemoryDataSource,
) : UserRepository {

    override fun getUsers() = userMemoryDataSource.getUsers().map { it.toModel() }

    override suspend fun getAmountUsers() = userMemoryDataSource.getNumUsers()

    override suspend fun getRemoteUsers(results: Int): Result<Unit> {
        return userRemoteDataSource.getUsers(results)
            .fold(
                onSuccess = { usersApiModel ->
                    storeUsers(usersApiModel.toModel())
                    Result.success(Unit)
                },
                onFailure = { throwable ->
                    Result.failure(throwable)
                })
    }

    private suspend fun storeUsers(users: List<UserModel>) {
        userMemoryDataSource.addUsers(users.toEntity())
    }

    override suspend fun deleteUser(userModel: UserModel): Boolean {
        return userMemoryDataSource.deleteUser(userModel.toEntity())
    }

    override fun getUser(email: String): Flow<Result<UserModel>> {
        val user = userMemoryDataSource.getUser(email)
        return user.map {
            if (it != null) Result.success(it.toModel())
            else Result.failure(Throwable(USER_NOT_FOUND))
        }
    }

    companion object {
        const val USER_NOT_FOUND = "User not found"
    }
}
