package kosenda.makecolor.core.ui.state

import android.graphics.Bitmap
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.util.randomColorData

data class PictureUiState(
    val colorData: ColorData = randomColorData(),
    val bitmap: Bitmap? = null,
    val paletteColors: PaletteColorsState = PaletteColorsState(),
)
