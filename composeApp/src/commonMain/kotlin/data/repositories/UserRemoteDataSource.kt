package data.repositories

import data.models.UsersApiModel

interface UserRemoteDataSource {

    suspend fun getUsers(results: Int): Result<UsersApiModel>
}