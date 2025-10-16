package pl.inno4med.asystent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
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
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import pl.inno4med.components.BottomNavItem
import pl.inno4med.components.SimpleSmallAppBar
import pl.inno4med.components.Transitions
import pl.inno4med.components.rememberSlideDistance
import kotlin.reflect.typeOf

@Serializable
object TodoGraph {
    @Serializable
    object TodoListRoute

    @Serializable
    object TestRoute

    @Serializable
    data class TodoDetailsRoute(val todo: Todo)
}

@Serializable
object SecondGraph {
    @Serializable
    object SecondRoute

    @Serializable
    object SecondDetailsRoute
}

@Serializable
object ThirdGraph {
    @Serializable
    object ThirdRoute

    @Serializable
    object ThirdDetailsRoute
}

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

@OptIn(ExperimentalMaterial3Api::class)
private fun NavGraphBuilder.todoGraph() {
    navigation<TodoGraph>(TodoGraph.TodoListRoute) {
        composable<TodoGraph.TodoListRoute>(deepLinks = listOf(navDeepLink {
            uriPattern = "todo.com/{name}"
        })) { backstack ->
            val name = backstack.arguments?.read { getStringOrNull("name") }

            Scaffold(
                topBar = { SimpleSmallAppBar("xd") },
//                containerColor = Color.Red
            ) { innerPadding ->
                TodoList(name, innerPadding)
            }
        }
        composable<TodoGraph.TestRoute>(deepLinks = listOf(navDeepLink {
            action = "pl.inno4med.asystent.SHORTCUT"
        })) {
            Text("Test")
        }
        composable<TodoGraph.TodoDetailsRoute>(typeMap = mapOf(typeOf<Todo>() to navTypeOf<Todo>())) { backStackEntry ->
            val todo: TodoGraph.TodoDetailsRoute = backStackEntry.toRoute()

            TodoDetails(todo.todo)
        }
    }
}

private fun NavGraphBuilder.secondGraph() {
    navigation<SecondGraph>(SecondGraph.SecondRoute) {
        composable<SecondGraph.SecondRoute> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val navController = LocalNavController.current

                Button({ navController.navigate(SecondGraph.SecondDetailsRoute) }) {
                    Text("Second")
                }
            }
        }
        composable<SecondGraph.SecondDetailsRoute> {
            Text("Second details")
        }
    }
}

private fun NavGraphBuilder.thirdGraph() {
    navigation<ThirdGraph>(ThirdGraph.ThirdRoute) {
        composable<ThirdGraph.ThirdRoute> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val navController = LocalNavController.current

                Button({ navController.navigate(ThirdGraph.ThirdDetailsRoute) }) {
                    Text("Third")
                }
            }
        }
        composable<ThirdGraph.ThirdDetailsRoute> {
            val navController = LocalNavController.current
            Text("Third details", modifier = Modifier.clickable {
                navController.navigateUp()
            })
        }
    }
}

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController provided")
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

private inline fun <reified T> navTypeOf(
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
