package kosenda.makecolor.core.ui.feature.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.ui.feature.common.extension.noRippleClickable

@Composable
fun ColorCircle(color: Color, size: Dp, onClick: (Color) -> Unit) {
    val buttonScaleState = rememberButtonScaleState()
    // 色が透明な時は表示しない
    if (color != Color.Transparent) {
        Box(
            modifier = Modifier
                .padding(start = 8.dp)
                .size(size)
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
