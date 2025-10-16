package pl.inno4med.components

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry

actual class Transitions actual constructor(slideDistance: Int) {
    actual val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
        {
            if (isSameNavGraph) materialSharedAxisXIn(true, slideDistance)
            else materialFadeThroughIn()
        }
    actual val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
        {
            if (isSameNavGraph) materialSharedAxisXOut(true, slideDistance)
            else materialFadeThroughOut()
        }

    actual val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
        {
            if (isSameNavGraph) materialSharedAxisXIn(false, slideDistance)
            else materialFadeThroughIn()
        }
    actual val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
        {
            if (isSameNavGraph) materialSharedAxisXOut(false, slideDistance)
            else materialFadeThroughOut()
        }
}

// Taken from https://github.com/fornewid/material-motion-compose

private const val ProgressThreshold = 0.35f

private const val DefaultMotionDuration = 300

private const val ForOutgoing = (DefaultMotionDuration * ProgressThreshold).toInt()

private const val ForIncoming = DefaultMotionDuration - ForOutgoing

private fun materialFadeThroughIn() = fadeIn(
    animationSpec = tween(
        durationMillis = ForIncoming,
        delayMillis = ForOutgoing,
        easing = LinearOutSlowInEasing,
    ),
) + scaleIn(
    animationSpec = tween(
        durationMillis = ForIncoming,
        delayMillis = ForOutgoing,
        easing = LinearOutSlowInEasing,
    ),
    initialScale = .92f,
)

private fun materialFadeThroughOut() = fadeOut(
    animationSpec = tween(
        durationMillis = ForOutgoing,
        delayMillis = 0,
        easing = FastOutLinearInEasing,
    ),
)

private fun materialSharedAxisXIn(
    forward: Boolean,
    slideDistance: Int,
) = slideInHorizontally(
    animationSpec = tween(
        durationMillis = DefaultMotionDuration,
        easing = FastOutSlowInEasing,
    ),
    initialOffsetX = {
        if (forward) slideDistance else -slideDistance
    },
) + fadeIn(
    animationSpec = tween(
        durationMillis = ForIncoming,
        delayMillis = ForOutgoing,
        easing = LinearOutSlowInEasing,
    ),
)


private fun materialSharedAxisXOut(
    forward: Boolean,
    slideDistance: Int,
) = slideOutHorizontally(
    animationSpec = tween(
        durationMillis = DefaultMotionDuration,
        easing = FastOutSlowInEasing,
    ),
    targetOffsetX = {
        if (forward) -slideDistance else slideDistance
    },
) + fadeOut(
    animationSpec = tween(
        durationMillis = ForOutgoing,
        delayMillis = 0,
        easing = FastOutLinearInEasing,
    ),
)
