@file:OptIn(ExperimentalTestApi::class, ExperimentalMaterial3Api::class)

package pl.inno4med.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.runComposeUiTest
import androidx.navigation.NavController
import kotlin.test.Test

class AppBarsTest {
    @Test
    fun `should not have back button`() = runComposeUiTest {
        setContent {
            SimpleSmallAppBar(
                title = "Title",
                navController = null
            )
        }

        onNodeWithContentDescription("Back button").assertDoesNotExist()
    }

    @Test
    fun `should have back button`() = runComposeUiTest {
        setContent {
            SimpleSmallAppBar(
                title = "Title",
                navController = MockNavController()
            )
        }

        onNodeWithContentDescription("Back button").assertExists()
    }
}

private class MockNavController : NavController()
