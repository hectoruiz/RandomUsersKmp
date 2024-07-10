package domain.usecases

import domain.models.UserModel
import domain.repositories.UserRepository

class DeleteUserUseCase(private val userRepository: UserRepository) {

    suspend fun deleteUser(user: UserModel) = userRepository.deleteUser(user)
}
