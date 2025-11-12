@file:OptIn(ExperimentalMaterial3Api::class)

package pl.inno4med.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import asystent.components.generated.resources.Res
import asystent.components.generated.resources.back_button
import asystent.components.generated.resources.ios_back_button
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * @param navController if null, no back button will be shown
 * */
@Composable
public fun SimpleSmallAppBar(
    title: StringResource,
    navController: NavController? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    SimpleSmallAppBar(stringResource(title), navController, scrollBehavior, actions)
}

/**
 * @param navController if null, no back button will be shown
 * */
@Composable
public fun SimpleMediumAppBar(
    title: StringResource,
    navController: NavController? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    SimpleMediumAppBar(stringResource(title), navController, scrollBehavior, actions)
}

/**
 * @param navController if null, no back button will be shown
 * */
@Composable
public fun SimpleLargeAppBar(
    title: StringResource,
    navController: NavController? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {
    SimpleLargeAppBar(stringResource(title), navController, scrollBehavior, colors)
}

/**
 * @param navController if null, no back button will be shown
 * */
@Composable
public fun SimpleSmallAppBar(
    title: String,
    navController: NavController? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = { AutoBackButton(navController) },
        scrollBehavior = scrollBehavior,
        actions = actions,
    )
}

/**
 * @param navController if null, no back button will be shown
 * */
@Composable
public fun SimpleMediumAppBar(
    title: String,
    navController: NavController? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {
    MediumTopAppBar(
        title = { Text(title) },
        navigationIcon = { AutoBackButton(navController) },
        scrollBehavior = scrollBehavior,
        actions = actions,
        colors = colors,
    )
}

/**
 * @param navController if null, no back button will be shown
 * */
@Composable
public fun SimpleLargeAppBar(
    title: String,
    navController: NavController? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {
    LargeTopAppBar(
        title = { Text(title, maxLines = 2, overflow = TextOverflow.Ellipsis) },
        navigationIcon = { AutoBackButton(navController) },
        scrollBehavior = scrollBehavior,
        colors = colors,
    )
}

internal expect val isChevronIcon: Boolean

@Composable
private fun AutoBackButton(navController: NavController? = null) {
    if (navController == null) return

    IconButton(onClick = {
        navController.navigateUp()
    }) {
        Icon(
            painterResource(if (isChevronIcon) Res.drawable.ios_back_button else Res.drawable.back_button),
            contentDescription = stringResource(Res.string.back_button),
        )
    }
}
