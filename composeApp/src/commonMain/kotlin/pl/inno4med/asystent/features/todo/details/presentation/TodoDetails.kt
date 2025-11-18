package pl.inno4med.asystent.features.todo.details.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import pl.inno4med.asystent.features.todo.list.domain.Todo
import pl.inno4med.asystent.navigation.LocalNavController
import pl.inno4med.asystent.utils.Result
import pl.inno4med.asystent.utils.UnitCallback
import pl.inno4med.components.SimpleSmallAppBar
import pl.inno4med.components.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetails(vm: TodoDetailsViewModel = koinViewModel()) {
    val navController = LocalNavController.current

    Scaffold(topBar = { SimpleSmallAppBar("details", navController) }) { padding ->
        TodoDetailsContent(vm.todo, vm::loadTodo, padding)
    }
}

@Composable
fun TodoDetailsContent(
    todoState: StateFlow<Result<Todo>>,
    retry: UnitCallback,
    padding: PaddingValues,
) {
    val todo by todoState.collectAsStateWithLifecycle()

    todo.switchPlaceholder(retry = retry, emptyItem = Todo.empty()) { todo ->

        Column(Modifier.padding(padding)) {

            AsyncImage(
                todo.image,
                contentDescription = todo.name,
                alignment = Alignment.BottomEnd,
                modifier = Modifier.shimmer().size(400.dp).background(Color.Red)
            )
            Text("Details: ${todo.name} ${todo.image}", Modifier.shimmer())


            val navController = LocalNavController.current
            TextButton({ navController?.navigateUp() }) {
                Text("Back")
            }
        }
    }
}

@Preview
@Composable
private fun TodoDetailsPreview() {
    val todo = MutableStateFlow(Result.Success(Todo.empty()))

    TodoDetailsContent(todo, {}, PaddingValues(16.dp))
}
