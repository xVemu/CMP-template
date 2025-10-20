package pl.inno4med.components

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

public actual class Transitions actual constructor(slideDistance: Int) {
    public actual val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
        {
            if (isSameNavGraph) IosTransitions.enterTransition(this)
            else fadeIn(animationSpec = tween(700))
        }
    public actual val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
        {
            if (isSameNavGraph) IosTransitions.exitTransition(this)
            else fadeOut(animationSpec = tween(700))
        }

    public actual val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
        {
            if (isSameNavGraph) IosTransitions.popEnterTransition(this)
            else fadeIn(animationSpec = tween(700))
        }
    public actual val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
        {
            if (isSameNavGraph) IosTransitions.popExitTransition(this)
            else fadeOut(animationSpec = tween(700))
        }
}

private object IosTransitions {
    val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Start, animationSpec = tween(
                durationMillis = 200, easing = LinearEasing
            )
        )
    }

    val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Start, animationSpec = tween(
                durationMillis = 200, easing = LinearEasing
            ), targetOffset = { fullOffset -> (fullOffset * 0.3f).toInt() })
    }

    val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
        {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End, animationSpec = tween(
                    durationMillis = 200, easing = LinearEasing
                ), initialOffset = { fullOffset -> (fullOffset * 0.3f).toInt() })
        }

    val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
        {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End, animationSpec = tween(
                    durationMillis = 200, easing = LinearEasing
                )
            )
        }
}
