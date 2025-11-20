package pl.inno4med.asystent.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import kotlin.reflect.KType

expect inline fun <reified T : Any> NavGraphBuilder.fullscreenDialog(
    typeMap: Map<KType, NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    dialogProperties: DialogProperties? = null,
    noinline content: @Composable (NavBackStackEntry) -> Unit,
)
