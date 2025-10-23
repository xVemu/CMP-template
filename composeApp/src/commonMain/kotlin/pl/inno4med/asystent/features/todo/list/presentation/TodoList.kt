package pl.inno4med.asystent.features.todo.list.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import pl.inno4med.asystent.navigation.LocalNavController
import pl.inno4med.asystent.navigation.TodoGraph
import pl.inno4med.components.PullToRefreshAndRetrySnackbarBox
import pl.inno4med.components.SimpleSmallAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoList(someText: String? = null, vm: TodoViewModel = koinViewModel()) {
    val result by vm.todos.collectAsStateWithLifecycle()

    Scaffold(topBar = { SimpleSmallAppBar("xd") }) { innerPadding ->
        result.switchRefresh(vm::refreshTodos) { todos, refreshing, exception ->

            PullToRefreshAndRetrySnackbarBox(
                isRefreshing = refreshing,
                onRefresh = vm::refreshTodos,
                modifier = Modifier.padding(innerPadding),
                forceShowSnackbar = exception != null
            ) {
                val navController = LocalNavController.current

                LazyColumn {
                    item {
                        TextButton({}) {
                            Text("Add $someText")
                        }
                    }

                    items(todos) { todo ->
                        Row {
                            AsyncImage(model = todo.image, contentDescription = todo.name)
                            Spacer(Modifier.width(8.dp))
                            TextButton({ navController.navigate(TodoGraph.TodoDetailsRoute(todo.id)) }) {
                                Text("${todo.name} ${todo.image}")
                            }
                        }
                    }
                }
            }
        }
    }
}
