package kosenda.makecolor.model.usecase

import kosenda.makecolor.model.data.ColorData
import kosenda.makecolor.model.ColorType
import kosenda.makecolor.model.util.cmykToRgb
import kosenda.makecolor.model.util.hsvToRGB
import kosenda.makecolor.model.util.rgbToCmyk
import kosenda.makecolor.model.util.rgbToHex
import kosenda.makecolor.model.util.rgbToHsv
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
