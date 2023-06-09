package kosenda.makecolor.feature.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.core.domain.InputColorTextState
import kosenda.makecolor.core.domain.InputColorTextUseCase
import kosenda.makecolor.core.domain.UpdateColorDataUseCase
import kosenda.makecolor.core.domain.UpdateOtherColorUseCase
import kosenda.makecolor.core.model.data.CMYKColor
import kosenda.makecolor.core.model.data.ColorType
import kosenda.makecolor.core.model.data.ColorTypeWithHex
import kosenda.makecolor.core.model.data.HEXColor
import kosenda.makecolor.core.model.data.HSVColor
import kosenda.makecolor.core.model.data.RGBColor
import kosenda.makecolor.core.model.data.StringResource
import kosenda.makecolor.core.ui.state.InputTextUiState
import kosenda.makecolor.core.ui.state.TextCMYK
import kosenda.makecolor.core.ui.state.TextHSV
import kosenda.makecolor.core.ui.state.TextRGB
import kosenda.makecolor.core.util.hexToRGB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.math.roundToInt

abstract class InputTextViewModel : ViewModel() {
    abstract val uiState: StateFlow<InputTextUiState>
    abstract fun updateSelectColorType(index: Int)
    abstract fun updateInputText(rgbType: RGBColor, value: String): StringResource?
    abstract fun updateInputText(cmykType: CMYKColor, value: String): StringResource?
    abstract fun updateInputText(hsvType: HSVColor, value: String): StringResource?
    abstract fun updateInputText(hex: String): StringResource?
}

@HiltViewModel
class InputTextViewModelImpl @Inject constructor(
    val inputColorTextUseCase: InputColorTextUseCase,
    val updateColorDataUseCase: UpdateColorDataUseCase,
    val updateOtherColorUseCase: UpdateOtherColorUseCase,
) : InputTextViewModel() {
    private val _uiState = MutableStateFlow(InputTextUiState())
    override val uiState: StateFlow<InputTextUiState> = _uiState.asStateFlow()

    override fun updateSelectColorType(index: Int) {
        val newColorType = when (index) {
            ColorTypeWithHex.RGB.index -> ColorTypeWithHex.RGB
            ColorTypeWithHex.CMYK.index -> ColorTypeWithHex.CMYK
            ColorTypeWithHex.HSV.index -> ColorTypeWithHex.HSV
            ColorTypeWithHex.HEX.index -> ColorTypeWithHex.HEX
            else -> throw IllegalArgumentException()
        }
        _uiState.update { it.copy(selectColorType = newColorType) }
    }

    override fun updateInputText(rgbType: RGBColor, value: String): StringResource? {
        val errorText: StringResource? =
            when (val inputColorTextState = inputColorTextUseCase(rgbType, value)) {
                is InputColorTextState.INVALID -> inputColorTextState.errorText
                else -> null
            }
        _uiState.update {
            it.copy(
                textRGB = when (rgbType) {
                    RGBColor.RED -> it.textRGB.copy(red = value)
                    RGBColor.GREEN -> it.textRGB.copy(green = value)
                    RGBColor.BLUE -> it.textRGB.copy(blue = value)
                },
            )
        }
        if (errorText == null && value.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    colorData = updateColorDataUseCase(it.colorData, rgbType, value.toFloat()),
                )
            }
            updateInputTextFromColorData()
        }
        return errorText
    }

    override fun updateInputText(cmykType: CMYKColor, value: String): StringResource? {
        val errorText: StringResource? =
            when (val inputColorTextState = inputColorTextUseCase(cmykType, value)) {
                is InputColorTextState.INVALID -> inputColorTextState.errorText
                else -> null
            }
        _uiState.update {
            it.copy(
                textCMYK = when (cmykType) {
                    CMYKColor.CYAN -> it.textCMYK.copy(cyan = value)
                    CMYKColor.MAGENTA -> it.textCMYK.copy(magenta = value)
                    CMYKColor.YELLOW -> it.textCMYK.copy(yellow = value)
                    CMYKColor.BLACK -> it.textCMYK.copy(black = value)
                },
            )
        }
        if (errorText == null && value.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    colorData = updateColorDataUseCase(it.colorData, cmykType, value.toFloat()),
                )
            }
            updateInputTextFromColorData()
        }
        return errorText
    }

    override fun updateInputText(hsvType: HSVColor, value: String): StringResource? {
        val errorText: StringResource? =
            when (val inputColorTextState = inputColorTextUseCase(hsvType, value)) {
                is InputColorTextState.INVALID -> inputColorTextState.errorText
                else -> null
            }
        _uiState.update {
            it.copy(
                textHSV = when (hsvType) {
                    HSVColor.HUE -> it.textHSV.copy(hue = value)
                    HSVColor.SATURATION -> it.textHSV.copy(saturation = value)
                    HSVColor.VALUE -> it.textHSV.copy(value = value)
                },
            )
        }
        if (errorText == null && value.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    colorData = updateColorDataUseCase(it.colorData, hsvType, value.toFloat()),
                )
            }
            updateInputTextFromColorData()
        }
        return errorText
    }

    override fun updateInputText(hex: String): StringResource? {
        val errorText: StringResource? =
            when (val inputColorTextState = inputColorTextUseCase(HEXColor.HEX, hex)) {
                is InputColorTextState.INVALID -> inputColorTextState.errorText
                else -> null
            }
        _uiState.update { it.copy(hex = hex) }
        if (errorText == null && hex.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    colorData = updateOtherColorUseCase(
                        it.colorData.copy(rgb = hexToRGB(hex = hex)),
                        ColorType.RGB,
                    ),
                )
            }
            updateInputTextFromColorData()
        }
        return errorText
    }

    private fun updateInputTextFromColorData() {
        uiState.value.colorData.run {
            _uiState.update { inputTextUiState ->
                inputTextUiState.copy(
                    textRGB = TextRGB(
                        red = rgb.red.roundToInt().toString(),
                        green = rgb.green.roundToInt().toString(),
                        blue = rgb.blue.roundToInt().toString(),
                    ),
                    textCMYK = TextCMYK(
                        cyan = cmyk.cyan.roundToInt().toString(),
                        magenta = cmyk.magenta.roundToInt().toString(),
                        yellow = cmyk.yellow.roundToInt().toString(),
                        black = cmyk.black.roundToInt().toString(),
                    ),
                    textHSV = TextHSV(
                        hue = hsv.hue.roundToInt().toString(),
                        saturation = hsv.saturation.roundToInt().toString(),
                        value = hsv.value.roundToInt().toString(),
                    ),
                    hex = hex.toString(),
                )
            }
        }
    }
}

class PreviewInputTextViewModel : InputTextViewModel() {
    override val uiState: StateFlow<InputTextUiState> = MutableStateFlow(InputTextUiState())
    override fun updateSelectColorType(index: Int) {}
    override fun updateInputText(rgbType: RGBColor, value: String): StringResource? = null
    override fun updateInputText(cmykType: CMYKColor, value: String): StringResource? = null
    override fun updateInputText(hsvType: HSVColor, value: String): StringResource? = null
    override fun updateInputText(hex: String): StringResource? = null
}
