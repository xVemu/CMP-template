package pl.inno4med.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.min

public data class ScrollIndicatorStyle(
    val dotSize: Dp = 10.dp,
    val dotSpacing: Dp = 4.dp,
    val maxVisibleDots: Int = 5,
    val activeDotColor: Color,
    val inactiveDotColor: Color,
)

@Composable
public fun ScrollIndicator(
    scrollState: LazyListState,
    style: ScrollIndicatorStyle = ScrollIndicatorStyle(
        activeDotColor = MaterialTheme.colorScheme.primary,
        inactiveDotColor = MaterialTheme.colorScheme.outline.copy(alpha = .3f),
    ),
    modifier: Modifier = Modifier,
) {
    val count by derivedStateOf {
        scrollState.layoutInfo.totalItemsCount
    }

    val coroutineScope = rememberCoroutineScope()

    ScrollingDotsIndicator(
        modifier = modifier,
        style = style,
        offset = {
            scrollState.scrollIndicatorState!!.scrollOffset / (scrollState.scrollIndicatorState!!.contentSize - scrollState.layoutInfo.viewportSize.width).toFloat() * (count - 1)
        },
        count = count,
        onDotClick = { index ->
            coroutineScope.launch {
                scrollState.animateScrollToItem(index)
            }
        }
    )
}

@Composable
public fun ScrollIndicator(
    scrollState: ScrollState,
    count: Int,
    style: ScrollIndicatorStyle = ScrollIndicatorStyle(
        activeDotColor = MaterialTheme.colorScheme.primary,
        inactiveDotColor = MaterialTheme.colorScheme.outline.copy(alpha = .3f),
    ),
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    ScrollingDotsIndicator(
        offset = { scrollState.value / scrollState.maxValue.toFloat() * (count - 1) },
        count = count,
        style = style,
        modifier = modifier,
        onDotClick = { index ->
            coroutineScope.launch {
                scrollState.animateScrollTo(
                    (scrollState.maxValue * (index / (count - 1).toFloat())).toInt()
                )
            }
        })
}

@Composable
private fun ScrollingDotsIndicator(
    offset: () -> Float,
    count: Int,
    style: ScrollIndicatorStyle,
    modifier: Modifier = Modifier,
    onDotClick: (Int) -> Unit,
) {
    if (count <= 1) return

    val totalWidth = style.run {
        (dotSize + dotSpacing) * min(count, maxVisibleDots)
    }

    val switchPoint = style.maxVisibleDots / 2

    Canvas(
        modifier = modifier.clearAndSetSemantics {}.width(totalWidth).height(style.dotSize)
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    val current = offset().toInt()

                    val firstVisibleDot =
                        if (current < switchPoint || count - 1 < style.maxVisibleDots) 0
                        else min(current - switchPoint, count - style.maxVisibleDots)
                    val lastVisibleDot = min(firstVisibleDot + style.maxVisibleDots, count - 1)

                    var finalOffset = 0F
                    for (index in firstVisibleDot..lastVisibleDot) {
                        finalOffset += style.dotSize.toPx() + style.dotSpacing.toPx()
                        if (tapOffset.x <= finalOffset) {
                            onDotClick(index)
                            break
                        }
                    }
                }
            }) {
        // Stolen form https://github.com/Milad-Akarie/smooth_page_indicator
        val offset = offset()
        val current = offset.toInt()

        val firstVisibleDot = if (current < switchPoint || count - 1 < style.maxVisibleDots) 0
        else min(current - switchPoint, count - style.maxVisibleDots)

        val lastVisibleDot = min(firstVisibleDot + style.maxVisibleDots, count - 1)
        val inPreScrollRange = current < switchPoint
        val inAfterScrollRange = current >= (count - 1) - switchPoint
        val willStartScrolling = (current + 1) == switchPoint + 1
        val willStopScrolling = current + 1 == (count - 1) - switchPoint

        val dotOffset = offset - current
        val distance = (style.dotSize + style.dotSpacing).toPx()

        val drawingAnchor =
            if (inPreScrollRange || inAfterScrollRange) -(firstVisibleDot * distance)
            else -((offset - switchPoint) * distance)

        val smallDotScale = .66f
        val activeScale = .0f

        for (index in firstVisibleDot..lastVisibleDot) {
            val color = when {
                index == current -> lerp(
                    style.activeDotColor, style.inactiveDotColor, dotOffset
                )

                index == firstVisibleDot && offset > count - 1 -> lerp(
                    style.inactiveDotColor, style.activeDotColor, dotOffset
                )

                index - 1 == current -> lerp(
                    style.inactiveDotColor, style.activeDotColor, dotOffset
                )

                else -> style.inactiveDotColor
            }

            val scale = when {
                index == current -> if (offset > count - 1 && count > style.maxVisibleDots) 1f - (smallDotScale * dotOffset)
                else 1f - (activeScale * dotOffset)

                index == firstVisibleDot && offset > count - 1 -> if (count <= style.maxVisibleDots) 1 + (activeScale * dotOffset)
                else smallDotScale + (((1 - smallDotScale) + activeScale) * dotOffset)

                index - 1 == current -> 1f + (activeScale * dotOffset)

                count - 1 < style.maxVisibleDots -> 1f

                index == firstVisibleDot -> when {
                    willStartScrolling -> (1.0f * (1.0f - dotOffset))
                    inAfterScrollRange -> smallDotScale
                    !inPreScrollRange -> smallDotScale * (1.0f - dotOffset)
                    else -> 1F
                }

                index == firstVisibleDot + 1 && !(inPreScrollRange || inAfterScrollRange) -> 1.0f - (dotOffset * (1.0f - smallDotScale))

                index == lastVisibleDot - 1 -> when {
                    inPreScrollRange -> smallDotScale
                    !inAfterScrollRange -> smallDotScale + ((1.0f - smallDotScale) * dotOffset)
                    else -> 1F
                }

                index == lastVisibleDot -> when {
                    inPreScrollRange -> 0f
                    willStopScrolling -> dotOffset
                    !inAfterScrollRange -> smallDotScale * dotOffset
                    else -> 1F
                }

                else -> 1F
            }

            val xPos = style.dotSize.toPx() / 2f + drawingAnchor + (index * distance)

            drawCircle(
                color = color, center = Offset(
                    x = xPos + style.dotSpacing.toPx() / 2,
                    y = size.height / 2f,
                ), radius = style.dotSize.toPx() / 2f * scale
            )
        }
    }
}
