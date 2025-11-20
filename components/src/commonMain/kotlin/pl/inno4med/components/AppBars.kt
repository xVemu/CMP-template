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
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import asystent.components.generated.resources.Res
import asystent.components.generated.resources.back_button
import asystent.components.generated.resources.close_button
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * @param useCloseButton true -> close button, false -> back button, null -> no button
 * */
@Composable
public fun SimpleSmallAppBar(
    title: StringResource,
    useCloseButton: Boolean?,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    SimpleSmallAppBar(stringResource(title), useCloseButton, scrollBehavior, actions)
}

/**
 * @param useCloseButton true -> close button, false -> back button, null -> no button
 * */
@Composable
public fun SimpleMediumAppBar(
    title: StringResource,
    useCloseButton: Boolean?,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    SimpleMediumAppBar(stringResource(title), useCloseButton, scrollBehavior, actions)
}

/**
 * @param navController if null, no back button will be shown
 * */
@Composable
public fun SimpleLargeAppBar(
    title: StringResource,
    useCloseButton: Boolean?,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {
    SimpleLargeAppBar(stringResource(title), useCloseButton, scrollBehavior, colors)
}

/**
 * @param useCloseButton true -> close button, false -> back button, null -> no button
 * */
@Composable
public fun SimpleSmallAppBar(
    title: String,
    useCloseButton: Boolean?,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = { AutoBackButton(useCloseButton) },
        scrollBehavior = scrollBehavior,
        actions = actions,
    )
}

/**
 * @param useCloseButton true -> close button, false -> back button, null -> no button
 * */
@Composable
public fun SimpleMediumAppBar(
    title: String,
    useCloseButton: Boolean?,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {
    MediumTopAppBar(
        title = { Text(title) },
        navigationIcon = { AutoBackButton(useCloseButton) },
        scrollBehavior = scrollBehavior,
        actions = actions,
        colors = colors,
    )
}

/**
 * @param useCloseButton true -> close button, false -> back button, null -> no button
 * */
@Composable
public fun SimpleLargeAppBar(
    title: String,
    useCloseButton: Boolean?,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {
    LargeTopAppBar(
        title = { Text(title, maxLines = 2, overflow = TextOverflow.Ellipsis) },
        navigationIcon = { AutoBackButton(useCloseButton) },
        scrollBehavior = scrollBehavior,
        colors = colors,
    )
}

internal expect val backIcon: DrawableResource

@Composable
private fun AutoBackButton(useCloseButton: Boolean?) {
    if (useCloseButton == null) return

    val navController = LocalNavController.current

    IconButton(onClick = {
        navController?.navigateUp()
    }) {
        Icon(
            painterResource(
                if (useCloseButton) Res.drawable.close_button else backIcon
            ),
            contentDescription = stringResource(Res.string.back_button),
        )
    }
}

/** It's `null` in tests*/
public val LocalNavController: ProvidableCompositionLocal<NavController?> =
    staticCompositionLocalOf {
        null
    }
