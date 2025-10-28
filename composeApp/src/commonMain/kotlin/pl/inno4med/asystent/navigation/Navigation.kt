package pl.inno4med.asystent.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import asystent.composeapp.generated.resources.Res
import asystent.composeapp.generated.resources.bottom_nav_1
import asystent.composeapp.generated.resources.bottom_nav_2
import asystent.composeapp.generated.resources.bottom_nav_3
import asystent.composeapp.generated.resources.square
import asystent.composeapp.generated.resources.square_filled
import com.eygraber.uri.Uri
import kotlinx.serialization.json.Json
import pl.inno4med.components.BottomNavItem
import pl.inno4med.components.Transitions
import pl.inno4med.components.rememberSlideDistance

val bottomItems = listOf(
    BottomNavItem(
        TodoGraph,
        Res.drawable.square,
        Res.drawable.square_filled,
        Res.string.bottom_nav_1,
    ),
    BottomNavItem(
        SecondGraph,
        Res.drawable.square,
        Res.drawable.square_filled,
        Res.string.bottom_nav_2
    ),
    BottomNavItem(
        ThirdGraph,
        Res.drawable.square,
        Res.drawable.square_filled,
        Res.string.bottom_nav_3
    )
)

/** It's `null` in tests*/
val LocalNavController = staticCompositionLocalOf<NavController?> {
    null
}

@Composable
fun NavigationHost(navController: NavHostController) {
    DisposableEffect(Unit) {
        IosQuickActionsHandler.listener = { action ->
            navController.navigate(NavDeepLinkRequest(null, action, null))
        }

        onDispose {
            IosQuickActionsHandler.listener = null
        }
    }

    val slideDistance = rememberSlideDistance()
    val transitions = remember(slideDistance) {
        Transitions(slideDistance)
    }

    NavHost(
        navController,
        startDestination = TodoGraph,
        enterTransition = transitions.enterTransition,
        exitTransition = transitions.exitTransition,
        popEnterTransition = transitions.popEnterTransition,
        popExitTransition = transitions.popExitTransition,
    ) {
        todoGraph()
        secondGraph()
        thirdGraph()
    }
}

inline fun <reified T> navTypeOf(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: SavedState, key: String): T? =
        bundle.read {
            getStringOrNull(key)?.let(json::decodeFromString)
        }

    override fun parseValue(value: String): T = json.decodeFromString(Uri.decode(value))

    override fun serializeAsValue(value: T): String = Uri.encode(json.encodeToString(value))

    override fun put(bundle: SavedState, key: String, value: T) =
        bundle.write {
            putString(key, json.encodeToString(value))
        }
}

object IosQuickActionsHandler {
    // Storage for when a URI arrives before the listener is set up
    private var cached: String? = null

    var listener: ((action: String) -> Unit)? = null
        set(value) {
            field = value
            if (value != null) {
                // When a listener is set and `cached` is not empty,
                // immediately invoke the listener with the cached URI
                cached?.let { value.invoke(it) }
                cached = null
            }
        }

    // When a new URI arrives, cache it.
    // If the listener is already set, invoke it and clear the cache immediately.
    fun onAction(action: String) {
        cached = action
        listener?.let {
            it.invoke(action)
            cached = null
        }
    }
}
