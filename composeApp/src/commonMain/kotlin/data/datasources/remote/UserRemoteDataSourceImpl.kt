package data.datasources.remote

import data.models.UsersApiModel
import data.repositories.UserRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class UserRemoteDataSourceImpl(private val httpClient: HttpClient) : UserRemoteDataSource {

    override suspend fun getUsers(results: Int): Result<UsersApiModel> {
        return try {
            val response = httpClient.get("/?page=1&seed=abc&exc=dob,login,id,nat&noinfo") {
                url {
                    parameters.append(QUERY, results.toString())
                }
            }
            Result.success(response.body())
        } catch (exception: Exception) {
            Result.failure(Throwable(exception.message))
        }
    }

    companion object {
        const val QUERY = "results"
    }
}
