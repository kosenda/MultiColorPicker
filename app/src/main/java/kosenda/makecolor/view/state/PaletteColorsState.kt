package kosenda.makecolor.view.state

import androidx.compose.ui.graphics.Color

data class PaletteColorsState(
    val lightVibrantColor: Color = Color.Transparent,
    val vibrantColor: Color = Color.Transparent,
    val darkVibrantColor: Color = Color.Transparent,
    val lightMutedColor: Color = Color.Transparent,
    val mutedColor: Color = Color.Transparent,
    val darkMutedColor: Color = Color.Transparent,
)
