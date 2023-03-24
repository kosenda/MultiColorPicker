package kosenda.makecolor.view.component.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import kosenda.makecolor.view.theme.MakeColorTheme

@Composable
fun ClassicColorPickerCard(
    defaultColor: Color,
    onColorChange: (HsvColor) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        ClassicColorPicker(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenWidthDp.dp * 8 / 10)
                .padding(all = 16.dp),
            color = HsvColor.from(color = defaultColor),
            showAlphaBar = false,
            onColorChanged = onColorChange,
        )
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
