package kosenda.makecolor.core.model.data

import androidx.compose.ui.graphics.Color

enum class RGBColor(val char: String, val maxValue: Float, val color: Color?) {
    RED(char = "R", maxValue = 255f, color = Red),
    GREEN(char = "G", maxValue = 255f, color = Green),
    BLUE(char = "B", maxValue = 255f, color = Blue),
}

enum class CMYKColor(val char: String, val maxValue: Float, val color: Color?) {
    CYAN(char = "C", maxValue = 100f, color = Cyan),
    MAGENTA(char = "M", maxValue = 100f, color = Magenta),
    YELLOW(char = "Y", maxValue = 100f, color = Yellow),
    BLACK(char = "K", maxValue = 100f, color = null),
}

enum class HSVColor(val char: String, val maxValue: Float, val color: Color?) {
    HUE(char = "H", maxValue = 360f, color = null),
    SATURATION(char = "S", maxValue = 255f, color = null),
    VALUE(char = "V", maxValue = 255f, color = null),
}

enum class HEXColor {
    HEX,
}

val Red = Color.Red
val Green = Color.Green
val Blue = Color(0xFF4D88FF)

val Cyan = Color.Cyan
val Magenta = Color.Magenta
val Yellow = Color(0xFFEBD300)