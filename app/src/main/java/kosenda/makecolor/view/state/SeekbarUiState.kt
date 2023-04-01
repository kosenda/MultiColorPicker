package kosenda.makecolor.view.state

import kosenda.makecolor.model.ColorType
import kosenda.makecolor.model.data.ColorData
import kosenda.makecolor.model.util.randomColorData

data class SeekbarUiState(
    val colorData: ColorData = randomColorData(),
    val selectColorType: ColorType = ColorType.RGB,
)
