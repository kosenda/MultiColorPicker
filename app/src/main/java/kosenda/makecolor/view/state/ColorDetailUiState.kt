package kosenda.makecolor.view.state

import kosenda.makecolor.model.data.ColorData
import kosenda.makecolor.model.util.randomColorData

data class ColorDetailUiState(
    val memo: String = "",
    val categoryName: String = "カテゴリ-",
    val colorData: ColorData = randomColorData(),
    val complementaryColorData: ColorData = randomColorData(),
    val oppositeColorData: ColorData = randomColorData(),
)
