package di

import androidx.lifecycle.SavedStateHandle
import data.api.remote.ApiClient
import data.datasources.memory.UserMemoryDataSourceImpl
import data.datasources.remote.UserRemoteDataSourceImpl
import data.repositories.UserMemoryDataSource
import data.repositories.UserRemoteDataSource
import data.repositories.UserRepositoryImpl
import domain.repositories.UserRepository
import domain.usecases.DeleteUserUseCase
import domain.usecases.FetchUsersUseCase
import domain.usecases.GetUserUseCase
import domain.usecases.GetUsersUseCase
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ui.userdetail.UserDetailViewModel
import ui.userlist.UserListViewModel

private val appModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    factory { DeleteUserUseCase(get()) }
    factory { FetchUsersUseCase(get()) }
    factory { GetUsersUseCase(get()) }
    factory { GetUserUseCase(get()) }
}

private val networkModule = module {
    single { ApiClient().httpClient }
    single<UserRemoteDataSource> { UserRemoteDataSourceImpl(get()) }
}

private val memoryModule = module {
    single<UserMemoryDataSource> { UserMemoryDataSourceImpl(get()) }
}

private val viewModelModule = module {
    viewModel {
        UserListViewModel(get(), get(), get())
    }
    viewModel { (handle: SavedStateHandle) ->
        UserDetailViewModel(savedStateHandle = handle, get())
    }
}

val allModules = listOf(appModule, networkModule, memoryModule, viewModelModule)
