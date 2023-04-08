package kosenda.makecolor.core.util

import android.graphics.Color.rgb
import androidx.compose.ui.graphics.Color
import com.godaddy.android.colorpicker.HsvColor
import kosenda.makecolor.core.model.data.CMYK
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.model.data.HEX
import kosenda.makecolor.core.model.data.HSV
import kosenda.makecolor.core.model.data.RGB
import kosenda.makecolor.core.model.data.RGBColor
import kotlin.math.max
import kotlin.math.min

val ThinBlack = Color(0xFF141414)
val ThinWhite = Color(0xFFEBEBEB)

fun blackOrWhiteFromRGB(rgb: RGB): Color {
    return when {
        rgb.red + rgb.green + rgb.blue > 370f || rgb.green > 210f -> ThinBlack
        else -> ThinWhite
    }
}

fun hexToHexStrWithSharp(hex: HEX) =
    "#%s%s%s".format(
        hex.red,
        hex.green,
        hex.blue,
    )

fun rgbToCmyk(rgb: RGB): CMYK {
    val red: Float = (rgb.red / 255f).coerceAtMost(1f)
    val green: Float = (rgb.green / 255f).coerceAtMost(1f)
    val blue: Float = (rgb.blue / 255f).coerceAtMost(1f)
    val black: Float = when {
        red == 1f || green == 1f || blue == 1f -> 0f
        else -> ((1f - (max(red, max(green, blue)))) * 100f).coerceAtMost(100f)
    }
    val cyan: Float = when {
        black != 1f -> ((1f - red - black) / (1f - black) * 100f).coerceAtMost(100f)
        else -> 0f
    }
    val magenta: Float = when {
        black != 1f -> ((1f - green - black) / (1f - black) * 100f).coerceAtMost(100f)
        else -> 0f
    }
    val yellow: Float = when {
        black != 1f -> ((1f - blue - black) / (1f - black) * 100f).coerceAtMost(100f)
        else -> 0f
    }
    return CMYK(cyan = cyan, magenta = magenta, yellow = yellow, black = black)
}

fun cmykToRgb(cmyk: CMYK): RGB = RGB(
    red = 255f * (100f - cmyk.cyan) * (100f - cmyk.black) / 10000f,
    green = 255f * (100f - cmyk.magenta) * (100f - cmyk.black) / 10000f,
    blue = 255f * (100f - cmyk.yellow) * (100f - cmyk.black) / 10000f,
)

fun rgbToComplementaryRgb(rgb: RGB): RGB {
    val sum = max(rgb.red, max(rgb.green, rgb.blue)) + min(rgb.red, min(rgb.green, rgb.blue))
    return RGB(red = sum - rgb.red, green = sum - rgb.green, blue = sum - rgb.blue)
}

fun rgbToOppositeRgb(rgb: RGB): RGB {
    return RGB(red = 255f - rgb.red, green = 255f - rgb.green, blue = 255f - rgb.blue)
}

fun hsvToRGB(hsv: HSV): RGB {
    val max: Float = hsv.value
    val min: Float = max - (hsv.saturation / 255f) * max
    val r: Float
    val g: Float
    val b: Float
    when {
        hsv.hue < 60f -> {
            r = max
            g = (hsv.hue / 60f) * (max - min) + min
            b = min
        }
        hsv.hue < 120f -> {
            r = ((120f - hsv.hue) / 60f) * (max - min) + min
            g = max
            b = min
        }
        hsv.hue < 180f -> {
            r = min
            g = max
            b = ((hsv.hue - 120f) / 60f) * (max - min) + min
        }
        hsv.hue < 240f -> {
            r = min
            g = ((240f - hsv.hue) / 60f) * (max - min) + min
            b = max
        }
        hsv.hue < 300f -> {
            r = ((hsv.hue - 240f) / 60f) * (max - min) + min
            g = min
            b = max
        }
        else -> {
            r = max
            g = min
            b = ((360f - hsv.hue) / 60f) * (max - min) + min
        }
    }
    return RGB(
        red = r.toInt().toFloat(),
        green = g.toInt().toFloat(),
        blue = b.toInt().toFloat(),
    )
}

fun rgbToHsv(rgb: RGB): HSV {
    val r: Float = rgb.red
    val g: Float = rgb.green
    val b: Float = rgb.blue
    var h = 0f
    val max = max(r, max(g, b))
    val min = min(r, min(g, b))
    if (r == g && g == b) {
        // 0
    } else if (r == max) {
        h = 60f * ((g - b) / (max - min))
    } else if (g == max) {
        h = 60f * ((b - r) / (max - min)) + 120f
    } else if (b == max) {
        h = 60f * ((r - g) / (max - min)) + 240f
    }
    if (h < 0f) h += 360f

    var s = 0f
    if (max > 0f) s = (max - min) / max * 255f

    val v: Float = max
    return HSV(hue = h, saturation = s, value = v)
}

fun hsvColorToHsv(hsvColor: HsvColor): HSV {
    return HSV(
        hue = hsvColor.hue.toInt().toFloat(),
        saturation = (hsvColor.saturation * 255).toInt().toFloat(),
        value = (hsvColor.value * 255).toInt().toFloat(),
    )
}

fun hsvColorToHexStr(hsvColor: HsvColor): String {
    val hsv = hsvColorToHsv(hsvColor = hsvColor)
    return rgbToHex(rgb = hsvToRGB(hsv = hsv)).toString()
}

fun rgbToHex(rgb: RGB): HEX {
    val redInt = rgb.red.toInt()
    val greenInt = rgb.green.toInt()
    val blueInt = rgb.blue.toInt()
    val redStr = when {
        redInt > 15 -> "%X".format(redInt)
        else -> "0%X".format(redInt)
    }
    val greenStr = when {
        greenInt > 15 -> "%X".format(greenInt)
        else -> "0%X".format(greenInt)
    }
    val blueStr = when {
        blueInt > 15 -> "%X".format(blueInt)
        else -> "0%X".format(blueInt)
    }
    return HEX(
        red = redStr,
        green = greenStr,
        blue = blueStr,
    )
}

fun hexToRGB(hex: String): RGB {
    return RGB(
        red = hex.substring(0, 2).toInt(16).toFloat(),
        green = hex.substring(2, 4).toInt(16).toFloat(),
        blue = hex.substring(4, 6).toInt(16).toFloat(),
    )
}

fun colorToRGB(color: Color) = RGB(
    red = color.red * RGBColor.RED.maxValue,
    green = color.green * RGBColor.GREEN.maxValue,
    blue = color.blue * RGBColor.BLUE.maxValue,
)

fun hexToColor(hex: String): Color {
    val rgb = hexToRGB(hex = hex)
    return Color(red = rgb.red.toInt(), green = rgb.green.toInt(), blue = rgb.blue.toInt())
}

fun rgbToColorData(rgb: RGB): ColorData {
    return ColorData(
        rgb = rgb,
        cmyk = rgbToCmyk(rgb = rgb),
        hsv = rgbToHsv(rgb = rgb),
        hex = rgbToHex(rgb = rgb),
    )
}

fun rgbToColor(rgb: RGB) = Color(rgb(rgb.red.toInt(), rgb.green.toInt(), rgb.blue.toInt()))
