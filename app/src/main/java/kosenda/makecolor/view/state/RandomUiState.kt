package kosenda.makecolor.view.state

import kosenda.makecolor.model.ColorData
import kosenda.makecolor.model.RGB
import kosenda.makecolor.model.util.outputRandomRGBColors
import kosenda.makecolor.model.util.randomColorData
import kosenda.makecolor.view.code.RandomType

data class RandomUiState(
    val colorData: ColorData = randomColorData(),
    val randomRGBColors: List<RGB> = outputRandomRGBColors(randomType = RandomType.NOT_SPECIFIED),
    val selectRandomType: RandomType = RandomType.NOT_SPECIFIED,
)
