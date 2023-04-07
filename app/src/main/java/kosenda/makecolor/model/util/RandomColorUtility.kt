package kosenda.makecolor.model.util

import kosenda.makecolor.core.model.data.RGB
import kosenda.makecolor.view.code.RandomType

fun outputRandomRGBColors(randomType: RandomType, size: Int = 10): List<RGB> {
    return mutableListOf<RGB>().apply {
        repeat(size) {
            this.add(makeRandomRGB(randomType = randomType))
        }
    }
}

fun randomColorData() = rgbToColorData(rgb = makeRandomRGB(randomType = RandomType.NOT_SPECIFIED))

fun randomVividColorData() = rgbToColorData(rgb = makeRandomRGB(randomType = RandomType.Vivid))

fun randomHex() = rgbToHex(rgb = makeRandomRGB(randomType = RandomType.NOT_SPECIFIED)).toString()

private fun makeRandomRGB(randomType: RandomType): RGB {
    fun index2(index1: Int): Int {
        var index2 = (0..2).random()
        while (index1 == index2) {
            index2 = (0..2).random()
        }
        return index2
    }
    fun index3(index1: Int, index2: Int) = when (index1 + index2) {
        1 -> 2
        2 -> 1
        else -> 0
    }

    return when (randomType) {
        RandomType.NOT_SPECIFIED -> {
            RGB(
                red = (0..255).random().toFloat(),
                green = (0..255).random().toFloat(),
                blue = (0..255).random().toFloat(),
            )
        }
        RandomType.PASTEL -> {
            val tempRGB = mutableListOf(0f, 0f, 0f)
            val index1 = (0..2).random()
            val index2 = index2(index1)
            val index3 = index3(index1, index2)
            tempRGB[index1] = 255.toFloat()
            tempRGB[index2] = (158..255).random().toFloat()
            tempRGB[index3] = (158..224).random().toFloat()
            RGB(
                red = tempRGB[0],
                green = tempRGB[1],
                blue = tempRGB[2],
            )
        }
        RandomType.Vivid -> {
            val tempRGB = mutableListOf(0f, 0f, 0f)
            val index1 = (0..2).random()
            val index2 = index2(index1)
            val index3 = index3(index1, index2)
            tempRGB[index1] = 255.toFloat()
            tempRGB[index2] = (0..255).random().toFloat()
            tempRGB[index3] = (0..90).random().toFloat()
            RGB(
                red = tempRGB[0],
                green = tempRGB[1],
                blue = tempRGB[2],
            )
        }
        RandomType.BLACK_AND_WHITE -> {
            val value = (0..255).random().toFloat()
            RGB(
                red = value,
                green = value,
                blue = value,
            )
        }
    }
}
