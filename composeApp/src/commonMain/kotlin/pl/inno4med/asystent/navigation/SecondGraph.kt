package pl.inno4med.asystent.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import pl.inno4med.components.LocalNavController

@Serializable
object SecondGraph {
    @Serializable
    object SecondRoute

    @Serializable
    object SecondDetailsRoute
}

fun NavGraphBuilder.secondGraph() {
    navigation<SecondGraph>(SecondGraph.SecondRoute) {
        composable<SecondGraph.SecondRoute> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val navController = LocalNavController.current

                Button({ navController?.navigate(SecondGraph.SecondDetailsRoute) }) {
                    Text("Second")
                }
            }
        }
        composable<SecondGraph.SecondDetailsRoute> {
            Text("Second details")
        }
    }
}
