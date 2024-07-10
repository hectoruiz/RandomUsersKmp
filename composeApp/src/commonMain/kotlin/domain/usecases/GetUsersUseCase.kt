package domain.usecases

import domain.models.UserModel
import domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUsersUseCase(private val userRepository: UserRepository) {

    fun getUsers(): Flow<List<UserModel>> =
        userRepository.getUsers().map { userList -> userList.filter { it.isActive } }
}
