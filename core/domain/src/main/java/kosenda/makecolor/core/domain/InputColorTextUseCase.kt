package kosenda.makecolor.core.domain

import kosenda.makecolor.core.util.hexToRGB
import kosenda.makecolor.core.model.data.CMYKColor
import kosenda.makecolor.core.model.data.HEXColor
import kosenda.makecolor.core.model.data.HSVColor
import kosenda.makecolor.core.model.data.RGBColor
import kosenda.makecolor.core.model.data.StringResource
import kosenda.makecolor.core.resource.R
import timber.log.Timber
import javax.inject.Inject

sealed class InputColorTextState {
    object EMPTY : InputColorTextState()
    object OK : InputColorTextState()
    data class INVALID(val errorText: StringResource) : InputColorTextState()
}

class InputColorTextUseCase @Inject constructor() {
    operator fun invoke(colorType: Any, text: String): InputColorTextState {
        if (text.isEmpty()) return InputColorTextState.EMPTY
        runCatching {
            val maxValue = when (colorType) {
                RGBColor.RED -> RGBColor.RED.maxValue
                RGBColor.GREEN -> RGBColor.GREEN.maxValue
                RGBColor.BLUE -> RGBColor.BLUE.maxValue
                CMYKColor.CYAN -> CMYKColor.CYAN.maxValue
                CMYKColor.MAGENTA -> CMYKColor.MAGENTA.maxValue
                CMYKColor.YELLOW -> CMYKColor.YELLOW.maxValue
                CMYKColor.BLACK -> CMYKColor.BLACK.maxValue
                HSVColor.HUE -> HSVColor.HUE.maxValue
                HSVColor.SATURATION -> HSVColor.SATURATION.maxValue
                HSVColor.VALUE -> HSVColor.VALUE.maxValue
                HEXColor.HEX -> {
                    hexToRGB(hex = text) // RGBに変換できない場合にOKとならないようにする
                    if (text.length > 6) throw HexTextOverLengthException()
                    -1f
                }
                else -> throw IllegalArgumentException("不正な引数(colorType): $colorType")
            }
            when (maxValue) {
                -1f -> true to -1
                else -> (text.toFloat() in 0f..maxValue) to maxValue
            }
        }.onSuccess { (isOK, maxValue) ->
            return when {
                isOK -> InputColorTextState.OK
                else -> {
                    InputColorTextState.INVALID(
                        errorText = StringResource(
                            resId = R.string.please_enter,
                            strings = listOf("0", maxValue.toInt().toString()),
                        ),
                    )
                }
            }
        }.onFailure {
            return when (it) {
                is NumberFormatException,
                is HexTextOverLengthException,
                -> {
                    InputColorTextState.INVALID(StringResource(resId = R.string.invalid_value))
                }
                else -> {
                    Timber.e("type %s, text %s: %s".format(colorType, text, it))
                    InputColorTextState.INVALID(StringResource(resId = R.string.invalid_value))
                }
            }
        }
        throw IllegalArgumentException("type %s, text %s: %s".format(colorType, text, "不正な値"))
    }
}

private class HexTextOverLengthException : RuntimeException()
