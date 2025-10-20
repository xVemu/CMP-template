package pl.inno4med.components

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry

public expect class Transitions(slideDistance: Int) {
    public val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition
    public val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition

    public val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition
    public val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition
}

internal val AnimatedContentTransitionScope<NavBackStackEntry>.isSameNavGraph
    get() = initialState.destination.parent?.startDestinationRoute == targetState.destination.parent?.startDestinationRoute

@Composable
public fun rememberSlideDistance(
    slideDistance: Dp = 30.dp,
): Int {
    val density = LocalDensity.current
    return remember(density, slideDistance) {
        with(density) { slideDistance.roundToPx() }
    }
}
