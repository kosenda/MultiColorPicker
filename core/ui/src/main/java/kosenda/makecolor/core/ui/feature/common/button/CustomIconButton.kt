package kosenda.makecolor.core.ui.feature.common.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.ui.feature.common.rememberButtonScaleState
import kosenda.makecolor.core.ui.feature.theme.contentBrush
import kosenda.makecolor.core.ui.feature.theme.primaryBrush

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier
            .padding(start = 8.dp)
            .size(48.dp)
            .contentBrush(brush = primaryBrush()),
        onClick = onClick,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.size(28.dp),
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String,
    backGroundColor: Color = Color.Transparent,
    onClick: () -> Unit,
) {
    val buttonScaleState = rememberButtonScaleState()
    IconButton(
        modifier = modifier
            .size(48.dp)
            .scale(scale = buttonScaleState.animationScale.value),
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(containerColor = backGroundColor),
        interactionSource = buttonScaleState.interactionSource,
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.size(28.dp),
        )
    }
}
