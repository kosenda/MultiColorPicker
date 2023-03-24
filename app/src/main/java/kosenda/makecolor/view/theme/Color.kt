package kosenda.makecolor.view.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import kosenda.makecolor.view.LocalIsDark

val Red = Color.Red
val Green = Color.Green
val Blue = Color(0xFF4D88FF)

val Cyan = Color.Cyan
val Magenta = Color.Magenta
val Yellow = Color(0xFFEBD300)

val ThinBlack = Color(0xFF141414)
val ThinWhite = Color(0xFFEBEBEB)

@Composable
fun primaryBrush(): Brush = Brush.linearGradient(
    listOf(
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.primary,
    ),
)

@Composable
fun backgroundBrush(): Brush = Brush.verticalGradient(
    listOf(
        backgroundTopColor(),
        backgroundBottomColor(),
    ),
)

@Composable
fun backgroundTopColor(): Color {
    return when {
        LocalIsDark.current -> {
            MaterialTheme.colorScheme.surfaceVariant.changeBrightness(0.7f)
        }
        else -> {
            MaterialTheme.colorScheme.surfaceVariant.changeBrightness(1.1f)
        }
    }
}

@Composable
fun backgroundBottomColor(): Color {
    return when {
        LocalIsDark.current -> {
            MaterialTheme.colorScheme.secondaryContainer.changeBrightness(0.6f)
        }
        else -> {
            MaterialTheme.colorScheme.secondaryContainer
        }
    }
}

fun Modifier.contentBrush(brush: Brush) = this
    .graphicsLayer(alpha = 0.99f)
    .drawWithCache {
        onDrawWithContent {
            drawContent()
            drawRect(brush = brush, blendMode = BlendMode.SrcAtop)
        }
    }

fun Color.changeBrightness(value: Float = 1f) = this.copy(
    red = (this.red * value).coerceIn(0f, 1f),
    blue = (this.blue * value).coerceIn(0f, 1f),
    green = (this.green * value).coerceIn(0f, 1f),
)
