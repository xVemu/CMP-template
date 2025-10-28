package pl.inno4med.asystent.navigation

import androidx.compose.foundation.clickable
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

@Serializable
object ThirdGraph {
    @Serializable
    object ThirdRoute

    @Serializable
    object ThirdDetailsRoute
}

fun NavGraphBuilder.thirdGraph() {
    navigation<ThirdGraph>(ThirdGraph.ThirdRoute) {
        composable<ThirdGraph.ThirdRoute> {
            Box(Modifier.Companion.fillMaxSize(), contentAlignment = Alignment.Companion.Center) {
                val navController = LocalNavController.current

                Button({ navController?.navigate(ThirdGraph.ThirdDetailsRoute) }) {
                    Text("Third")
                }
            }
        }
        composable<ThirdGraph.ThirdDetailsRoute> {
            val navController = LocalNavController.current
            Text("Third details", modifier = Modifier.Companion.clickable {
                navController?.navigateUp()
            })
        }
    }
}
