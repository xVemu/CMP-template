package pl.inno4med.asystent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import com.eygraber.uri.Uri
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

@Serializable
object TodoRoute {
    @Serializable
    object TodoListRoute

    @Serializable
    data class TodoDetailsRoute(val todo: Todo)
}

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController provided")
}

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController, startDestination = TodoRoute) {
            navigation<TodoRoute>(TodoRoute.TodoListRoute) {
                composable<TodoRoute.TodoListRoute> {
                    TodoList()
                }
                composable<TodoRoute.TodoDetailsRoute>(typeMap = mapOf(typeOf<Todo>() to navTypeOf<Todo>())) { backStackEntry ->
                    val todo: TodoRoute.TodoDetailsRoute = backStackEntry.toRoute()

                    TodoDetails(todo.todo)
                }
            }
        }
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
