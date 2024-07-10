package navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import domain.commons.Error
import org.koin.compose.viewmodel.koinNavViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ui.userdetail.UserDetailScreen
import ui.userdetail.UserDetailViewModel
import ui.userlist.UserListScreen
import ui.userlist.UserListViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = START_DESTINATION) {
        composable(
            route = START_DESTINATION,
            enterTransition = {
                slideIn(initialOffset = { IntOffset(300, 200) }) + fadeIn(
                    tween(
                        ANIMATION_LIST_LENGTH
                    )
                )
            },
            popEnterTransition = {
                slideIn(initialOffset = { IntOffset(300, 200) }) + fadeIn(
                    tween(
                        ANIMATION_LIST_LENGTH
                    )
                )
            },
            exitTransition = {
                slideOut(targetOffset = { IntOffset(300, 200) }) + fadeOut(
                    tween(
                        ANIMATION_LIST_LENGTH
                    )
                )
            },
            popExitTransition = {
                slideOut(targetOffset = { IntOffset(300, 200) }) + fadeOut(
                    tween(
                        ANIMATION_LIST_LENGTH
                    )
                )
            }
        ) {
            val userListViewModel: UserListViewModel = koinViewModel()
            val uiState by userListViewModel.uiState.collectAsState()
            val users by userListViewModel.users.collectAsState(emptyList())
            val error by userListViewModel.error.collectAsState(Error.NoError)

            UserListScreen(
                users = users,
                uiState = uiState,
                error = error,
                onUserSearch = { userListViewModel.searchUsers(it) },
                onClickMoreUsers = { userListViewModel.getMoreUsers() },
                onDeleteUser = { userListViewModel.deleteUser(it) },
                navigateToDetail = {
                    navController.navigate("$FINAL_DESTINATION/$it")
                },
            )
        }
        composable(
            route = "$FINAL_DESTINATION/{$PARAM}",
            arguments = listOf(navArgument(PARAM) { type = NavType.StringType }),
            enterTransition = {
                slideIn(initialOffset = { IntOffset(300, 200) }) + fadeIn(
                    tween(
                        ANIMATION_DETAIL_LENGTH
                    )
                )
            },
            popEnterTransition = {
                slideIn(initialOffset = { IntOffset(300, 200) }) + fadeIn(
                    tween(
                        ANIMATION_DETAIL_LENGTH
                    )
                )
            },
            exitTransition = {
                slideOut(targetOffset = { IntOffset(300, 200) }) + fadeOut(
                    tween(
                        ANIMATION_DETAIL_LENGTH
                    )
                )
            },
            popExitTransition = {
                slideOut(targetOffset = { IntOffset(300, 200) }) + fadeOut(
                    tween(
                        ANIMATION_DETAIL_LENGTH
                    )
                )
            }
        ) {
            val argument = it.arguments?.getString(PARAM) ?: ""
            val userDetailViewModel: UserDetailViewModel = koinNavViewModel()
            val userDetailUiState by userDetailViewModel.userDetailUiState.collectAsState()

            UserDetailScreen(userDetailUiState = userDetailUiState) { navController.popBackStack() }
        }
    }
}

const val START_DESTINATION = "userList"
const val FINAL_DESTINATION = "userDetail"
const val PARAM = "email"
private const val ANIMATION_LIST_LENGTH = 1000
private const val ANIMATION_DETAIL_LENGTH = 500
