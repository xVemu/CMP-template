package pl.inno4med.asystent.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import androidx.savedstate.read
import kotlinx.serialization.Serializable
import pl.inno4med.asystent.features.todo.details.presentation.TodoDetails
import pl.inno4med.asystent.features.todo.list.presentation.TodoList


@Serializable
object TodoGraph {
    @Serializable
    object TodoListRoute

    @Serializable
    object TestRoute

    @Serializable
    data class TodoDetailsRoute(val todoId: Long)
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.todoGraph() {
    navigation<TodoGraph>(TodoGraph.TodoListRoute) {
        composable<TodoGraph.TodoListRoute>(deepLinks = listOf(navDeepLink {
            uriPattern = "todo.com/{name}"
        })) { backstack ->
            val name = backstack.arguments?.read { getStringOrNull("name") }

            TodoList(name)
        }

        composable<TodoGraph.TestRoute>(deepLinks = listOf(navDeepLink {
            action = "pl.inno4med.asystent.SHORTCUT"
        })) {
            Text("Test")
        }

        composable<TodoGraph.TodoDetailsRoute> { backStackEntry ->
            val todo: TodoGraph.TodoDetailsRoute = backStackEntry.toRoute()

            TodoDetails(todo.todoId)
        }
    }
}
