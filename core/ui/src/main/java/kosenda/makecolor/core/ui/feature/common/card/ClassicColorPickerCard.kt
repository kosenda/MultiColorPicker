package kosenda.makecolor.core.ui.feature.common.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme

@Composable
fun ClassicColorPickerCard(
    defaultColor: Color,
    onColorChange: (HsvColor) -> Unit,
) {
    val colorPickerSize = when {
        LocalConfiguration.current.screenWidthDp > LocalConfiguration.current.screenHeightDp -> {
            LocalConfiguration.current.screenHeightDp.dp * 0.7f
        }
        else -> {
            LocalConfiguration.current.screenWidthDp.dp
        }
    }
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            ClassicColorPicker(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(all = 16.dp)
                    .size(size = colorPickerSize),
                color = HsvColor.from(color = defaultColor),
                showAlphaBar = false,
                onColorChanged = onColorChange,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewClassicColorPickerCard_Light() {
    MakeColorTheme(isDarkTheme = false) {
        ClassicColorPickerCard(defaultColor = Color.Red) {}
    }
}

@Preview
@Composable
private fun PreviewClassicColorPickerCard_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        ClassicColorPickerCard(defaultColor = Color.Red) {}
    }
}
