package kosenda.makecolor.core.ui.feature.common.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.ui.state.PaletteColorsState
import kosenda.makecolor.core.ui.feature.common.ColorCircle
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme

@Composable
fun PaletteCircleCard(
    paletteColors: PaletteColorsState,
    horizontalPadding: Dp,
    updateColorData: (Color) -> Unit,
) {
    val screenMaxWidth = LocalConfiguration.current.screenWidthDp.dp - horizontalPadding * 2
    val circleSize = (screenMaxWidth - 8.dp - 16.dp) / 6 - 8.dp
    Card(
        modifier = Modifier.padding(vertical = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 16.dp)
                .padding(vertical = 16.dp),
        ) {
            listOf(
                paletteColors.lightVibrantColor,
                paletteColors.vibrantColor,
                paletteColors.darkVibrantColor,
                paletteColors.lightMutedColor,
                paletteColors.mutedColor,
                paletteColors.darkMutedColor,
            ).map {
                ColorCircle(
                    color = it,
                    size = circleSize,
                    onClick = updateColorData,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPaletteCircleCard() {
    val paletteColors = PaletteColorsState(
        lightVibrantColor = Color.Red,
        vibrantColor = Color.Red,
        darkVibrantColor = Color.Red,
        lightMutedColor = Color.Red,
        mutedColor = Color.Red,
        darkMutedColor = Color.Red,
    )
    Column {
        MakeColorTheme(isDarkTheme = false) {
            PaletteCircleCard(
                paletteColors = paletteColors,
                horizontalPadding = 16.dp,
                updateColorData = {},
            )
        }
        MakeColorTheme(isDarkTheme = true) {
            PaletteCircleCard(
                paletteColors = paletteColors,
                horizontalPadding = 16.dp,
                updateColorData = {},
            )
        }
    }
}
