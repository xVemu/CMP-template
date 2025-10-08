package pl.inno4med.asystent

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun TodoDetails(todo: Todo) {
    Column {
        Text("Details: ${todo.title} ${todo.content}")

        val navController = LocalNavController.current

        TextButton({ navController.navigateUp() }) {
            Text("Back")
        }
    }
}
