package ui.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.commons.Error
import domain.usecases.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val email: String = savedStateHandle[PARAM_KEY] ?: ""
    private val _userDetailUiState = MutableStateFlow(UserDetailUiState())
    val userDetailUiState: StateFlow<UserDetailUiState> = _userDetailUiState.asStateFlow()

    init {
        viewModelScope.launch {
            getUserUseCase.getUser(email).collect { result ->
                result.fold(
                    onSuccess = {
                        _userDetailUiState.update { state ->
                            state.copy(loading = false, user = it)
                        }
                    },
                    onFailure = {
                        _userDetailUiState.update { state ->
                            state.copy(loading = false, error = Error.Other)
                        }
                    }
                )
            }
        }
    }
}

const val PARAM_KEY = "email"
