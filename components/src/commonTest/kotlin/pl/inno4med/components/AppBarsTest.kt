@file:OptIn(ExperimentalTestApi::class, ExperimentalMaterial3Api::class)

package pl.inno4med.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test

class AppBarsTest {
    @Test
    fun `should not have back button`() = runComposeUiTest {
        setContent {
            SimpleSmallAppBar(
                title = "Title",
                useCloseButton = null
            )
        }

        onNodeWithContentDescription("Back button").assertDoesNotExist()
    }

    @Test
    fun `should have back button`() = runComposeUiTest {
        setContent {
            SimpleSmallAppBar(
                title = "Title",
                useCloseButton = false
            )
        }

        onNodeWithContentDescription("Back button").assertExists()
    }

    @Test
    fun `should have close button`() = runComposeUiTest {
        setContent {
            SimpleSmallAppBar(
                title = "Title",
                useCloseButton = true
            )
        }

        onNodeWithContentDescription("Back button").assertExists()
    }
}
