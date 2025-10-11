package pl.inno4med.asystent

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.serialization.Serializable

@Composable
fun TodoList(someText: String? = null) {
    val todos = rememberSaveable {
        mutableStateListOf(Todo("Sample 1", "Content 1"), Todo("Sample 2", "Content 2"))
    }

    val navController = LocalNavController.current

    LazyColumn {
        items(todos) { todo ->
            TextButton({ navController.navigate(TodoGraph.TodoDetailsRoute(todo)) }) {
                Text("${todo.title} ${todo.content}")
            }
        }
        item {
            TextButton({
                todos += Todo("Title ${todos.size + 1}", "Content ${todos.size + 1}")
            }) {
                Text("Add $someText")
            }
        }
    }
}

@Serializable
data class Todo(val title: String, val content: String)
