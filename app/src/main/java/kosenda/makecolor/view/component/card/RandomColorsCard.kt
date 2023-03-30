package kosenda.makecolor.view.component.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kosenda.makecolor.model.data.RGB
import kosenda.makecolor.model.util.outputRandomRGBColors
import kosenda.makecolor.model.util.rgbToColor
import kosenda.makecolor.view.code.RandomType
import kosenda.makecolor.view.component.ColorCircle
import kosenda.makecolor.view.theme.MakeColorTheme

@Composable
fun RandomColorsCard(
    randomRGBColors: List<RGB>,
    size: Dp,
    updateColorData: (Color) -> Unit,
) {
    Card(
        modifier = Modifier.padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
                .padding(end = 8.dp),
        ) {
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                for (i in 0..4) {
                    ColorCircle(
                        color = rgbToColor(randomRGBColors[i]),
                        size = size,
                        onClick = updateColorData,
                    )
                }
            }
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                for (i in 5..9) {
                    ColorCircle(
                        color = rgbToColor(randomRGBColors[i]),
                        size = size,
                        onClick = updateColorData,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRandomColorsCard_Light() {
    MakeColorTheme(isDarkTheme = false) {
        RandomColorsCard(
            randomRGBColors = outputRandomRGBColors(randomType = RandomType.PASTEL),
            size = 48.dp,
            updateColorData = {},
        )
    }
}

@Preview
@Composable
private fun PreviewRandomColorsCard_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        RandomColorsCard(
            randomRGBColors = outputRandomRGBColors(randomType = RandomType.PASTEL),
            size = 48.dp,
            updateColorData = {},
        )
    }
}
