package pl.inno4med.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationRailDefaults
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
fun PrimaryScaffold(
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
                    modifier = Modifier.windowInsetsPadding(
                        WindowInsets.displayCutout.only(
                            WindowInsetsSides.Start
                        )
                    ),
                    content = {
                        items(navController, currentEntry?.destination, bottomNavItems)
                    }
                )
            },
            layoutType = layoutType,
            content = {
                Box(
                    Modifier
                        .consumeWindowInsets(
                            when (layoutType) {
                                NavigationSuiteType.NavigationBar ->
                                    NavigationBarDefaults.windowInsets.only(WindowInsetsSides.Bottom)

                                NavigationSuiteType.NavigationRail ->
                                    NavigationRailDefaults.windowInsets.only(WindowInsetsSides.Start)

                                NavigationSuiteType.NavigationDrawer ->
                                    DrawerDefaults.windowInsets.only(WindowInsetsSides.Start)

                                else -> WindowInsets(0, 0, 0, 0)
                            }
                        )
                        // Adds padding to appbar and content for cutout on the right side in landscape
                        .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.End))
                        .consumeWindowInsets(WindowInsets.displayCutout.only(WindowInsetsSides.End))
                ) {
                    content()
                }
            }
        )
    }
}

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

data class BottomNavItem(
    val graph: Any,
    val icon: DrawableResource,
    val iconFilled: DrawableResource,
    val label: StringResource,
)


