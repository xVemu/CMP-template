package pl.inno4med.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuite
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.window.core.layout.WindowSizeClass
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
public fun PrimaryScaffold(
    navController: NavController,
    bottomNavItems: List<BottomNavItem>,
    content: @Composable () -> Unit,
) {
    Surface(
        color = NavigationSuiteScaffoldDefaults.containerColor,
        contentColor = NavigationSuiteScaffoldDefaults.contentColor
    ) {
        val layoutType = currentWindowAdaptiveInfo().calculateType()

        NavigationSuiteScaffoldLayout(
            navigationSuite = {
                val currentEntry by navController.currentBackStackEntryAsState()

                NavigationSuite(
                    layoutType = layoutType,
                    modifier = Modifier.platformNavigationBarInsets(),
                    content = {
                        items(navController, currentEntry?.destination, bottomNavItems)
                    }
                )
            },
            layoutType = layoutType,
            content = {
                Box(
                    Modifier
                        .then(
                            when (layoutType) {
                                NavigationSuiteType.NavigationBar ->
                                    WindowInsetsSides.Bottom

                                NavigationSuiteType.NavigationRail ->
                                    WindowInsetsSides.Start

                                NavigationSuiteType.NavigationDrawer ->
                                    WindowInsetsSides.Start

                                else -> null
                            }?.let { side ->
                                Modifier.consumeWindowInsets(
                                    WindowInsets.safeDrawing.only(
                                        side
                                    ),
                                )
                            } ?: Modifier)
                        // Adds padding to appbar and content for cutout and navigation bar on the right side in landscape
                        .platformEndBodyInsets()
                ) {
                    content()
                }
            }
        )
    }
}

@Composable
internal expect fun Modifier.platformNavigationBarInsets(): Modifier

@Composable
internal expect fun Modifier.platformEndBodyInsets(): Modifier

private fun NavigationSuiteScope.items(
    navController: NavController,
    currentDestination: NavDestination?,
    items: List<BottomNavItem>,
) {
    items.forEach { item ->
        val navGraph = currentDestination?.hierarchy?.find {
            it.hasRoute(item.graph::class)
        } as NavGraph?

        val selected = navGraph != null

        item(
            label = { Text(stringResource(item.label)) },
            selected = selected,
            icon = {
                Icon(
                    painterResource(if (selected) item.iconFilled else item.icon),
                    contentDescription = stringResource(item.label),
                )
            },
            onClick = onClick@{
                if (selected) {
                    navController.popBackStack(
                        navGraph.findStartDestination().id,
                        false,
                    )
                    return@onClick
                }

                navController.navigate(item.graph) {
                    popUpTo(navController.graph.findStartDestination().id) { // Deletes every destination except RootStartingDestination
                        saveState = true // Save state of deleted destinations
                    }
                    launchSingleTop = true // It's not necessary needed, but it's good practise
                    restoreState = true // Restore state of deleted destinations
                }
            },
        )
    }
}

private fun WindowAdaptiveInfo.calculateType(): NavigationSuiteType {
    return when {
        windowPosture.isTabletop -> NavigationSuiteType.NavigationBar
        // if is height.compact or width >= medium
        windowSizeClass.minHeightDp < WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND || windowSizeClass.isWidthAtLeastBreakpoint(
            WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
        ) -> NavigationSuiteType.NavigationRail

        else -> NavigationSuiteType.NavigationBar
    }
}

public data class BottomNavItem(
    val graph: Any,
    val icon: DrawableResource,
    val iconFilled: DrawableResource,
    val label: StringResource,
)


