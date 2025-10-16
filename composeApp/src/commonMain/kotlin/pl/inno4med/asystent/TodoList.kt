package pl.inno4med.asystent

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Composable
fun TodoList(someText: String? = null, innerPadding: PaddingValues = PaddingValues.Zero) {
    val todos = rememberSaveable {
        List(100) { index ->
            Todo("Sample $index", "Content $index")
        }.toMutableStateList()
    }

    val navController = LocalNavController.current

    LazyColumn(Modifier.fillMaxSize(), contentPadding = innerPadding) {
        item {
            TextButton({
                todos += Todo("Title ${todos.size + 1}", "Content ${todos.size + 1}")
            }) {
                Text("Add $someText")
            }
        }
        items(todos, key = { it.title }) { todo ->
            TextButton({ navController.navigate(TodoGraph.TodoDetailsRoute(todo)) }) {
                Text("${todo.title} ${todo.content}")
            }
        }
    }
}

@Serializable
data class Todo(val title: String, val content: String)
