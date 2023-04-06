package kosenda.makecolor.model.usecase

import kosenda.makecolor.model.ColorType
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.view.CMYKColor
import kosenda.makecolor.view.HSVColor
import kosenda.makecolor.view.RGBColor
import javax.inject.Inject

class UpdateColorDataUseCase @Inject constructor(
    val updateOtherColorUseCase: UpdateOtherColorUseCase,
) {
    operator fun invoke(colorData: ColorData, rgbType: RGBColor, value: Float): ColorData {
        val newColorData = colorData.copy(
            rgb = when (rgbType) {
                RGBColor.RED -> colorData.rgb.copy(red = value)
                RGBColor.GREEN -> colorData.rgb.copy(green = value)
                RGBColor.BLUE -> colorData.rgb.copy(blue = value)
            },
        )
        return updateOtherColorUseCase(colorData = newColorData, type = ColorType.RGB)
    }

    operator fun invoke(colorData: ColorData, cmykType: CMYKColor, value: Float): ColorData {
        val newColorData = colorData.copy(
            cmyk = when (cmykType) {
                CMYKColor.CYAN -> colorData.cmyk.copy(cyan = value)
                CMYKColor.MAGENTA -> colorData.cmyk.copy(magenta = value)
                CMYKColor.YELLOW -> colorData.cmyk.copy(yellow = value)
                CMYKColor.BLACK -> colorData.cmyk.copy(black = value)
            },
        )
        return updateOtherColorUseCase(colorData = newColorData, type = ColorType.CMYK)
    }

    operator fun invoke(colorData: ColorData, hsvType: HSVColor, value: Float): ColorData {
        val newColorData = colorData.copy(
            hsv = when (hsvType) {
                HSVColor.HUE -> colorData.hsv.copy(hue = value)
                HSVColor.SATURATION -> colorData.hsv.copy(saturation = value)
                HSVColor.VALUE -> colorData.hsv.copy(value = value)
            },
        )
        return updateOtherColorUseCase(colorData = newColorData, type = ColorType.HSV)
    }
}
