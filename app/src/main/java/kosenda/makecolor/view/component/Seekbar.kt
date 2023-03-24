package kosenda.makecolor.view.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.view.theme.MakeColorTheme

@Composable
fun Seekbar(
    value: Float,
    text: String,
    maxValue: Float,
    color: Color,
    onValueChange: (Float) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 8.dp),
    ) {
        Text(
            text = text,
            modifier = Modifier.width(30.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = value.toInt().toString(),
            modifier = Modifier.width(52.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..maxValue,
            colors = SliderDefaults.colors(
                thumbColor = color,
                activeTrackColor = color,
                inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        )
    }
}

@Preview
@Composable
private fun PreviewSeekbar_Light() {
    MakeColorTheme(isDarkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            Seekbar(value = 50f, text = "C", maxValue = 100f, color = Color.Cyan) {}
        }
    }
}

@Preview
@Composable
private fun PreviewSeekbar_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            Seekbar(value = 50f, text = "C", maxValue = 100f, color = Color.Cyan) {}
        }
    }
}
