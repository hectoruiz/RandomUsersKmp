package domain.usecases

import domain.repositories.UserRepository

class FetchUsersUseCase(private val userRepository: UserRepository) {

    suspend fun getRemoteUsers(isFirstCall: Boolean, defaultAmountUsers: Int = 20): Result<Unit> {
        val amountUsers = userRepository.getAmountUsers()
        return if (!isFirstCall) {
            userRepository.getRemoteUsers(amountUsers + defaultAmountUsers)
        } else {
            if (amountUsers < defaultAmountUsers) {
                userRepository.getRemoteUsers(amountUsers + defaultAmountUsers)
            } else Result.success(Unit)
        }
    }
}
