package com.hectoruiz.kmptest.ui.userlist

sealed class UserListUiState {

    data object Loading : UserListUiState()
    data object LoadMore : UserListUiState()
    data object NotLoading : UserListUiState()
}
