package pl.inno4med.asystent.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.dialog
import kotlin.reflect.KType

@OptIn(ExperimentalComposeUiApi::class)
actual inline fun <reified T : Any> NavGraphBuilder.fullscreenDialog(
    typeMap: Map<KType, NavType<*>>,
    deepLinks: List<NavDeepLink>,
    dialogProperties: DialogProperties?,
    noinline content: @Composable (NavBackStackEntry) -> Unit,
) {
    dialog<T>(
        typeMap,
        deepLinks,
        dialogProperties ?: DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
            usePlatformInsets = false,
        )
    ) {
        content(it)
    }
}
