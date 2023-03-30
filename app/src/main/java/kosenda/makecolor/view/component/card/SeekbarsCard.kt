package kosenda.makecolor.view.component.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.model.data.ColorData
import kosenda.makecolor.model.ColorType
import kosenda.makecolor.model.util.randomColorData
import kosenda.makecolor.view.CMYKColor
import kosenda.makecolor.view.HSVColor
import kosenda.makecolor.view.RGBColor
import kosenda.makecolor.view.component.Seekbar
import kosenda.makecolor.view.theme.MakeColorTheme

@Composable
fun SeekbarsCard(
    selectColorType: ColorType,
    onRGBColorChange: (RGBColor, Float) -> Unit,
    onCMYKColorChange: (CMYKColor, Float) -> Unit,
    onHSVColorChange: (HSVColor, Float) -> Unit,
    colorData: ColorData,
) {
    Card(
        modifier = Modifier.padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(all = 8.dp),
        ) {
            when (selectColorType) {
                ColorType.RGB -> {
                    RGBColor.values().map { rgb ->
                        Seekbar(
                            onValueChange = { new -> onRGBColorChange(rgb, new) },
                            value = when (rgb) {
                                RGBColor.RED -> colorData.rgb.red
                                RGBColor.GREEN -> colorData.rgb.green
                                RGBColor.BLUE -> colorData.rgb.blue
                            },
                            text = rgb.char,
                            maxValue = rgb.maxValue,
                            color = rgb.color!!,
                        )
                    }
                }
                ColorType.CMYK -> {
                    CMYKColor.values().map { cmyk ->
                        Seekbar(
                            onValueChange = { new -> onCMYKColorChange(cmyk, new) },
                            value = when (cmyk) {
                                CMYKColor.CYAN -> colorData.cmyk.cyan
                                CMYKColor.MAGENTA -> colorData.cmyk.magenta
                                CMYKColor.YELLOW -> colorData.cmyk.yellow
                                CMYKColor.BLACK -> colorData.cmyk.black
                            },
                            text = cmyk.char,
                            maxValue = cmyk.maxValue,
                            color = cmyk.color ?: MaterialTheme.colorScheme.primary,
                        )
                    }
                }
                ColorType.HSV -> {
                    HSVColor.values().map { hsv ->
                        Seekbar(
                            onValueChange = { new -> onHSVColorChange(hsv, new) },
                            value = when (hsv) {
                                HSVColor.HUE -> colorData.hsv.hue
                                HSVColor.SATURATION -> colorData.hsv.saturation
                                HSVColor.VALUE -> colorData.hsv.value
                            },
                            text = hsv.char,
                            maxValue = hsv.maxValue,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSeekbarsCard_Light() {
    MakeColorTheme(isDarkTheme = false) {
        SeekbarsCard(
            selectColorType = ColorType.CMYK,
            onRGBColorChange = { _, _ -> },
            onCMYKColorChange = { _, _ -> },
            onHSVColorChange = { _, _ -> },
            colorData = randomColorData(),
        )
    }
}

@Preview
@Composable
private fun PreviewSeekbarsCard_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        SeekbarsCard(
            selectColorType = ColorType.CMYK,
            onRGBColorChange = { _, _ -> },
            onCMYKColorChange = { _, _ -> },
            onHSVColorChange = { _, _ -> },
            colorData = randomColorData(),
        )
    }
}
