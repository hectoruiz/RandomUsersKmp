import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.api.memory.AppDatabase
import data.api.memory.UserDao
import data.api.memory.instantiateImpl
import di.allModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory
import platform.UIKit.UIDevice
import ui.DarkColorScheme
import ui.LightColorScheme
import ui.Typography

class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(allModules + platformModule())
        }
    }
}

fun getDatabase(): AppDatabase {
    val dbFilePath = NSHomeDirectory() + DATABASE_NAME
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
        factory = { AppDatabase::class.instantiateImpl() }
    )
        .fallbackToDestructiveMigration(true)
        .setDriver(BundledSQLiteDriver())
        .build()
}

actual fun platformModule() = module {
    single<AppDatabase> { getDatabase() }
    single<UserDao> { getDatabase().userDao() }
}

@Composable
actual fun RandomUsersTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
