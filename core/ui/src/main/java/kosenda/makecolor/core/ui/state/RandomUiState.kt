package kosenda.makecolor.core.ui.state

import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.model.data.RGB
import kosenda.makecolor.core.model.data.RandomType
import kosenda.makecolor.core.util.outputRandomRGBColors
import kosenda.makecolor.core.util.randomColorData

data class RandomUiState(
    val colorData: ColorData = randomColorData(),
    val randomRGBColors: List<RGB> = outputRandomRGBColors(randomType = RandomType.NOT_SPECIFIED),
    val selectRandomType: RandomType = RandomType.NOT_SPECIFIED,
)
