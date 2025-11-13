package pl.inno4med.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.PointerInputModifierNode
import androidx.compose.ui.node.SemanticsModifierNode
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.launch

public data class ShimmerScope(val enabled: Boolean)

/**
 * Provide this to indicate to children that they need to be shimmered
 * */
public val ShimmerCompositionLocal: ProvidableCompositionLocal<ShimmerScope> =
    compositionLocalOf { throw RuntimeException("ShimmerScopeMissing") }

/**
 * Apply this to every child that you want to be shimmered.
 * [ShimmerCompositionLocal] must be provided to work.
 * */
@Composable
public fun Modifier.shimmer(
    color: Color = MaterialTheme.colorScheme.onSurface,
    cornerRadius: CornerRadius = CornerRadius(12F, 12F),
): Modifier {
    val isEnabled = ShimmerCompositionLocal.current.enabled

    if (!isEnabled) return this

    return this then ShimmerNodeElement(color, cornerRadius)
}

private data class ShimmerNodeElement(val color: Color, val cornerRadius: CornerRadius) :
    ModifierNodeElement<ShimmerModifier>() {
    override fun create() = ShimmerModifier(color, cornerRadius)

    override fun update(node: ShimmerModifier) {
        node.color = color
        node.cornerRadius = cornerRadius
    }
}

private class ShimmerModifier(var color: Color, var cornerRadius: CornerRadius) : Modifier.Node(),
    DrawModifierNode,
    CompositionLocalConsumerModifierNode,
    SemanticsModifierNode, PointerInputModifierNode {

    private lateinit var alpha: Animatable<Float, AnimationVector1D>

    override fun ContentDrawScope.draw() {
        drawRoundRect(color, cornerRadius = cornerRadius, alpha = alpha.value)
    }

    override fun SemanticsPropertyReceiver.applySemantics() {}

    override val shouldClearDescendantSemantics: Boolean
        get() = true

    override fun onPointerEvent(
        pointerEvent: PointerEvent,
        pass: PointerEventPass,
        bounds: IntSize,
    ) {
        pointerEvent.changes.forEach { it.consume() }
    }

    override fun onCancelPointerInput() {}

    override fun onAttach() {
        super.onAttach()

        alpha = Animatable(.1F)

        coroutineScope.launch {
            alpha.animateTo(.3F, infiniteRepeatable(tween(600), RepeatMode.Reverse))
        }
    }
}
