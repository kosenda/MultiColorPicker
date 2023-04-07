package kosenda.makecolor.core.model.data

import kotlinx.serialization.Serializable

@Serializable
data class ColorData(
    val rgb: RGB,
    val cmyk: CMYK,
    val hsv: HSV,
    val hex: HEX,
)

@Serializable
data class RGB(
    val red: Float,
    val green: Float,
    val blue: Float,
) {
    override fun toString() = "R: %d G: %d B: %d".format(
        red.toInt(),
        green.toInt(),
        blue.toInt(),
    )
}

@Serializable
data class CMYK(
    val cyan: Float,
    val magenta: Float,
    val yellow: Float,
    val black: Float,
) {
    override fun toString() = "C: %d M: %d Y: %d K: %d".format(
        cyan.toInt(),
        magenta.toInt(),
        yellow.toInt(),
        black.toInt(),
    )
}

@Serializable
data class HSV(
    val hue: Float,
    val saturation: Float,
    val value: Float,
) {
    override fun toString() = "H: %d S: %d V: %d".format(
        hue.toInt(),
        saturation.toInt(),
        value.toInt(),
    )
}

@Serializable
data class HEX(
    val red: String,
    val green: String,
    val blue: String,
) {
    override fun toString() = "%s%s%s".format(red, green, blue)
}
