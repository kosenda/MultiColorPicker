package kosenda.makecolor.core.ui.feature.common.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme

@Composable
fun CustomRadioButton(
    isSelected: Boolean,
    buttonText: String,
    painter: Painter? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        painter?.let {
            Image(
                painter = it,
                contentDescription = buttonText,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
                contentScale = ContentScale.Fit,
                modifier = Modifier.padding(end = 4.dp).size(24.dp),
            )
        }
        Text(
            text = buttonText,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        RadioButton(
            selected = isSelected,
            colors = RadioButtonDefaults.colors(),
            onClick = onClick,
        )
    }
}

@Preview
@Composable
private fun PreviewCustomThemeRadioButton_Light() {
    MakeColorTheme(isDarkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            CustomRadioButton(
                buttonText = stringResource(id = R.string.dark_mode),
                isSelected = true,
                painter = painterResource(id = R.drawable.ic_baseline_brightness_2_24),
                onClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCustomThemeRadioButton_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            CustomRadioButton(
                buttonText = stringResource(id = R.string.dark_mode),
                isSelected = true,
                painter = painterResource(id = R.drawable.ic_baseline_brightness_2_24),
                onClick = {},
            )
        }
    }
}
