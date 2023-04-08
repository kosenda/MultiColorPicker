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
                colorData.copy(
                    rgb = cmykToRgb(colorData.cmyk),
                    hsv = rgbToHsv(colorData.rgb),
                    hex = rgbToHex(colorData.rgb),
                )
            }
            ColorType.HSV -> {
                colorData.copy(
                    rgb = hsvToRGB(colorData.hsv),
                    cmyk = rgbToCmyk(colorData.rgb),
                    hex = rgbToHex(colorData.rgb),
                )
            }
        }
    }
}
