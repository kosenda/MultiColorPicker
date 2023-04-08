package kosenda.makecolor.core.ui.state

import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.model.data.ColorType
import kosenda.makecolor.core.util.randomColorData

data class SeekbarUiState(
    val colorData: ColorData = randomColorData(),
    val selectColorType: ColorType = ColorType.RGB,
)
