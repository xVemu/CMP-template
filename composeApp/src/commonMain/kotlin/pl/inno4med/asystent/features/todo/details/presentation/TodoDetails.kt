package pl.inno4med.asystent.features.todo.details.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import pl.inno4med.asystent.LocalNavController
import pl.inno4med.components.SimpleSmallAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetails(todoId: Long) {
    val navController = LocalNavController.current
    Scaffold(topBar = { SimpleSmallAppBar("details", navController) }) {
        Column {
//            Text("Details: ${todo.title} ${todo.content}")

            TextButton({ navController.navigateUp() }) {
                Text("Back")
            }
        }
    }
}
