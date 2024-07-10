import androidx.compose.runtime.Composable
import org.koin.core.module.Module

interface Platform {
    val name: String
}

const val DATABASE_NAME = "database-random-user"

expect fun getPlatform(): Platform

expect fun platformModule(): Module

@Composable
expect fun RandomUsersTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit,
)

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class KoinInitializer {
    fun init()
}
