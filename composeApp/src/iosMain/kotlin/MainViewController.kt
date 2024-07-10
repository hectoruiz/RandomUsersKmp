import androidx.compose.ui.window.ComposeUIViewController
import androidx.navigation.compose.rememberNavController
import navigation.NavigationGraph
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() = ComposeUIViewController(
    configure = {
        KoinInitializer().init()
    }
) {
    val isDarkTheme =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
                UIUserInterfaceStyle.UIUserInterfaceStyleDark

    RandomUsersTheme(
        darkTheme = isDarkTheme,
        dynamicColor = false,
    ) {
        NavigationGraph(rememberNavController())
    }
}
