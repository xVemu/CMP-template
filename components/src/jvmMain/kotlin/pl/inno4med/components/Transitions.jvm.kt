package pl.inno4med.components

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

public actual class Transitions actual constructor(slideDistance: Int) {
    public actual val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
        {
            fadeIn(animationSpec = tween(700))
        }
    public actual val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
        {
            fadeOut(animationSpec = tween(700))
        }
    public actual val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
        enterTransition
    public actual val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
        exitTransition
}
