package kosenda.makecolor.view.component.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kosenda.makecolor.R
import kosenda.makecolor.view.rememberButtonScaleState
import kosenda.makecolor.view.theme.MakeColorTheme

@Composable
fun TonalButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    painter: Painter? = null,
    containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    height: Dp = 52.dp,
    onClick: () -> Unit,
) {
    val buttonScaleState = rememberButtonScaleState()
    val contentColor = when {
        enabled -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurface
    }
    FilledTonalButton(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .height(height = height)
            .scale(scale = buttonScaleState.animationScale.value),
        enabled = enabled,
        onClick = onClick,
        interactionSource = buttonScaleState.interactionSource,
        colors = ButtonDefaults.filledTonalButtonColors(containerColor = containerColor),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
    ) {
        if (painter != null) {
            Image(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(32.dp),
                painter = painter,
                contentDescription = text,
                colorFilter = ColorFilter.tint(color = contentColor),
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = contentColor,
        )
    }
}

@Preview
@Composable
private fun PreviewTonalButton_Light() {
    MakeColorTheme(isDarkTheme = false) {
        TonalButton(text = stringResource(id = R.string.add)) {}
    }
}

@Preview
@Composable
private fun PreviewTonalButton_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        TonalButton(text = stringResource(id = R.string.add)) {}
    }
}
