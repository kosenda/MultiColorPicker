package kosenda.makecolor.core.ui.feature.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.ui.feature.common.extension.noRippleClickable

private const val CIRCLE_PADDING = 8

@Composable
fun ColorCircle(modifier: Modifier = Modifier, color: Color, size: Dp, onClick: (Color) -> Unit) {
    val buttonScaleState = rememberButtonScaleState()
    var circleHeight by remember { mutableStateOf(0) }
    val density = LocalDensity.current.density
    // 色が透明な時は表示しない
    if (color != Color.Transparent) {
        Box(
            modifier = modifier
                .onSizeChanged { circleHeight = (it.width / density).toInt() - CIRCLE_PADDING }
                .padding(start = CIRCLE_PADDING.dp)
                .width(width = size)
                .height(height = circleHeight.dp)
                .scale(scale = buttonScaleState.animationScale.value)
                .clip(CircleShape)
                .background(color = color)
                .noRippleClickable(
                    interactionSource = buttonScaleState.interactionSource,
                    onClick = { onClick(color) },
                ),
        )
    }
}
