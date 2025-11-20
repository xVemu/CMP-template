package pl.inno4med.asystent.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.savedstate.read
import kotlinx.serialization.Serializable
import pl.inno4med.asystent.features.todo.details.presentation.TodoDetails
import pl.inno4med.asystent.features.todo.list.presentation.TodoList
import pl.inno4med.components.SimpleSmallAppBar


@Serializable
object TodoGraph {
    @Serializable
    object TodoListRoute

    @Serializable
    object TestRoute

    @Serializable
    object TodoDialog

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

        composable<TodoGraph.TodoDetailsRoute> {
            TodoDetails()
        }

        fullscreenDialog<TodoGraph.TodoDialog> {
            Scaffold(topBar = { SimpleSmallAppBar("Dialog", true) }) { padding ->
                Text("Dialog", Modifier.padding(padding))
            }
        }
    }
}
