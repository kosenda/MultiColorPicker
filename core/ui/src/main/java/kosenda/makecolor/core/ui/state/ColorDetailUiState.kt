package kosenda.makecolor.core.ui.state

import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.util.randomColorData

data class ColorDetailUiState(
    val memo: String = "",
    val categoryName: String = "カテゴリ-",
    val colorData: ColorData = randomColorData(),
    val complementaryColorData: ColorData = randomColorData(),
    val oppositeColorData: ColorData = randomColorData(),
)
