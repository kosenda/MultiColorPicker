package kosenda.makecolor.core.ui.feature.common.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.feature.common.rememberButtonScaleState
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme

@Composable
fun CustomOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter? = null,
    onClick: () -> Unit,
) {
    val buttonScaleState = rememberButtonScaleState()
    val contentColor = MaterialTheme.colorScheme.secondary
    OutlinedButton(
        modifier = modifier
            .padding(horizontal = 4.dp)
            .height(height = 52.dp)
            .scale(scale = buttonScaleState.animationScale.value),
        onClick = onClick,
        interactionSource = buttonScaleState.interactionSource,
    ) {
        Text(
            modifier = Modifier.padding(start = if (painter != null) 8.dp else 0.dp),
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = contentColor,
        )
        painter?.let {
            Image(
                painter = painter,
                contentDescription = text,
                colorFilter = ColorFilter.tint(color = contentColor),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(32.dp),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTonalButton_Light() {
    MakeColorTheme(isDarkTheme = false) {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            CustomOutlinedButton(text = stringResource(id = R.string.add)) {}
            CustomOutlinedButton(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = R.string.add),
                painter = painterResource(id = R.drawable.baseline_chevron_right_24),
            ) {}
        }
    }
}

@Preview
@Composable
private fun PreviewTonalButton_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            CustomOutlinedButton(text = stringResource(id = R.string.add)) {}
            CustomOutlinedButton(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = R.string.add),
                painter = painterResource(id = R.drawable.baseline_chevron_right_24),
            ) {}
        }
    }
}
