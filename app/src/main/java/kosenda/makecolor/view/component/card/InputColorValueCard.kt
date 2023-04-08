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
import kosenda.makecolor.core.model.data.CMYKColor
import kosenda.makecolor.core.model.data.ColorTypeWithHex
import kosenda.makecolor.core.model.data.HSVColor
import kosenda.makecolor.core.model.data.RGBColor
import kosenda.makecolor.core.model.data.StringResource
import kosenda.makecolor.core.ui.state.InputTextUiState
import kosenda.makecolor.view.component.textfield.ColorTextField
import kosenda.makecolor.view.theme.MakeColorTheme

@Composable
fun InputColorValueCard(
    selectColorType: ColorTypeWithHex,
    onRGBTextChange: (RGBColor, String) -> StringResource?,
    onCMYKTextChange: (CMYKColor, String) -> StringResource?,
    onHSVTextChange: (HSVColor, String) -> StringResource?,
    onHexTextChange: (String) -> StringResource?,
    uiState: InputTextUiState,
) {
    Card(
        modifier = Modifier.padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(all = 8.dp),
        ) {
            when (selectColorType) {
                ColorTypeWithHex.RGB -> {
                    RGBColor.values().map { rgb ->
                        ColorTextField(
                            text = rgb.char,
                            inputText = when (rgb) {
                                RGBColor.RED -> uiState.textRGB.red
                                RGBColor.GREEN -> uiState.textRGB.green
                                RGBColor.BLUE -> uiState.textRGB.blue
                            },
                            onValueChange = { text -> onRGBTextChange(rgb, text) },
                        )
                    }
                }
                ColorTypeWithHex.CMYK -> {
                    CMYKColor.values().map { cmyk ->
                        ColorTextField(
                            text = cmyk.char,
                            inputText = when (cmyk) {
                                CMYKColor.CYAN -> uiState.textCMYK.cyan
                                CMYKColor.MAGENTA -> uiState.textCMYK.magenta
                                CMYKColor.YELLOW -> uiState.textCMYK.yellow
                                CMYKColor.BLACK -> uiState.textCMYK.black
                            },
                            onValueChange = { text -> onCMYKTextChange(cmyk, text) },
                        )
                    }
                }
                ColorTypeWithHex.HSV -> {
                    HSVColor.values().map { hsv ->
                        ColorTextField(
                            text = hsv.char,
                            inputText = when (hsv) {
                                HSVColor.HUE -> uiState.textHSV.hue
                                HSVColor.SATURATION -> uiState.textHSV.saturation
                                HSVColor.VALUE -> uiState.textHSV.value
                            },
                            onValueChange = { text -> onHSVTextChange(hsv, text) },
                        )
                    }
                }
                ColorTypeWithHex.HEX -> {
                    ColorTextField(
                        text = ColorTypeWithHex.HEX.name,
                        inputText = uiState.hex,
                        isHex = true,
                        onValueChange = onHexTextChange,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewInputColorValueCard_Light() {
    MakeColorTheme(isDarkTheme = false) {
        InputColorValueCard(
            selectColorType = ColorTypeWithHex.RGB,
            onRGBTextChange = { _, _ -> null },
            onCMYKTextChange = { _, _ -> null },
            onHSVTextChange = { _, _ -> null },
            onHexTextChange = { null },
            uiState = InputTextUiState(),
        )
    }
}

@Preview
@Composable
fun PreviewInputColorValueCard_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        InputColorValueCard(
            selectColorType = ColorTypeWithHex.RGB,
            onRGBTextChange = { _, _ -> null },
            onCMYKTextChange = { _, _ -> null },
            onHSVTextChange = { _, _ -> null },
            onHexTextChange = { null },
            uiState = InputTextUiState(),
        )
    }
}
