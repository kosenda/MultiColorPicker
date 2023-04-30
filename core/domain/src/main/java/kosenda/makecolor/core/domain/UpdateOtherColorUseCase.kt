package kosenda.makecolor.core.domain

import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.model.data.ColorType
import kosenda.makecolor.core.util.cmykToRgb
import kosenda.makecolor.core.util.hsvToRGB
import kosenda.makecolor.core.util.rgbToCmyk
import kosenda.makecolor.core.util.rgbToHex
import kosenda.makecolor.core.util.rgbToHsv
import javax.inject.Inject

class UpdateOtherColorUseCase @Inject constructor() {
    operator fun invoke(colorData: ColorData, type: ColorType): ColorData {
        return when (type) {
            ColorType.RGB -> {
                colorData.copy(
                    cmyk = rgbToCmyk(colorData.rgb),
                    hsv = rgbToHsv(colorData.rgb),
                    hex = rgbToHex(colorData.rgb),
                )
            }
            ColorType.CMYK -> {
                val rgb = cmykToRgb(colorData.cmyk)
                colorData.copy(
                    rgb = rgb,
                    hsv = rgbToHsv(rgb),
                    hex = rgbToHex(rgb),
                )
            }
            ColorType.HSV -> {
                val rgb = hsvToRGB(colorData.hsv)
                colorData.copy(
                    rgb = rgb,
                    cmyk = rgbToCmyk(rgb),
                    hex = rgbToHex(rgb),
                )
            }
        }
    }
}
