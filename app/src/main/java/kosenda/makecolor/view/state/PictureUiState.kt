package kosenda.makecolor.view.state

import android.graphics.Bitmap
import kosenda.makecolor.model.data.ColorData
import kosenda.makecolor.model.util.randomColorData

data class PictureUiState(
    val colorData: ColorData = randomColorData(),
    val bitmap: Bitmap? = null,
    val paletteColors: PaletteColorsState = PaletteColorsState(),
)
