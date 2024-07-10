package ui.userdetail

import domain.commons.Error
import domain.models.Gender
import domain.models.UserModel

data class UserDetailUiState(
    val loading: Boolean = true,
    val user: UserModel = UserModel(
        gender = Gender.UNSPECIFIED,
        name = "",
        email = "",
        phone = "",
        picture = "",
        thumbnail = "",
        registeredDate = "",
        address = "",
        location = "",
        isActive = false
    ),
    val error: Error = Error.NoError,
)
