package kosenda.makecolor.view.state

import kosenda.makecolor.model.ColorTypeWithHex
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.model.util.randomColorData

data class InputTextUiState(
    val colorData: ColorData = randomColorData(),
    val selectColorType: ColorTypeWithHex = ColorTypeWithHex.RGB,
    val textRGB: TextRGB = TextRGB(),
    val textCMYK: TextCMYK = TextCMYK(),
    val textHSV: TextHSV = TextHSV(),
    val hex: String = "",
)

data class TextRGB(
    val red: String = "",
    val green: String = "",
    val blue: String = "",
)

data class TextCMYK(
    val cyan: String = "",
    val magenta: String = "",
    val yellow: String = "",
    val black: String = "",
)

data class TextHSV(
    val hue: String = "",
    val saturation: String = "",
    val value: String = "",
)
