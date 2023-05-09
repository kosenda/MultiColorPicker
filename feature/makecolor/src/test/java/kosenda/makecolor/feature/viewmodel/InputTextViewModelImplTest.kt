package kosenda.makecolor.feature.viewmodel

import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.domain.InputColorTextUseCase
import kosenda.makecolor.core.domain.UpdateColorDataUseCase
import kosenda.makecolor.core.domain.UpdateOtherColorUseCase
import kosenda.makecolor.core.model.data.CMYKColor
import kosenda.makecolor.core.model.data.ColorTypeWithHex
import kosenda.makecolor.core.model.data.HSVColor
import kosenda.makecolor.core.model.data.RGBColor
import kosenda.makecolor.core.util.hexToRGB
import org.junit.Test

class InputTextViewModelImplTest {

    private val viewModel = InputTextViewModelImpl(
        inputColorTextUseCase = InputColorTextUseCase(),
        updateColorDataUseCase = UpdateColorDataUseCase(
            updateOtherColorUseCase = UpdateOtherColorUseCase()
        ),
        updateOtherColorUseCase = UpdateOtherColorUseCase(),
    )

    @Test
    fun updateSelectColorType_everyIndex_settingColorType() {
        // Indexを設定したときにUiStateのSelectColorTypeが設定されることを確認
        assertThat(viewModel.uiState.value.selectColorType).isEqualTo(ColorTypeWithHex.RGB)
        viewModel.updateSelectColorType(ColorTypeWithHex.CMYK.index)
        assertThat(viewModel.uiState.value.selectColorType).isEqualTo(ColorTypeWithHex.CMYK)
        viewModel.updateSelectColorType(ColorTypeWithHex.RGB.index)
        assertThat(viewModel.uiState.value.selectColorType).isEqualTo(ColorTypeWithHex.RGB)
        viewModel.updateSelectColorType(ColorTypeWithHex.HSV.index)
        assertThat(viewModel.uiState.value.selectColorType).isEqualTo(ColorTypeWithHex.HSV)
        viewModel.updateSelectColorType(ColorTypeWithHex.HEX.index)
        assertThat(viewModel.uiState.value.selectColorType).isEqualTo(ColorTypeWithHex.HEX)
    }

    @Test
    fun updateInputText_rgbColorAndNotNumValue_errorTextIsNotNull() {
        // RGBColorと数値以外の文字列を設定したときにUiStateのErrorTextが設定されることを確認
        assertThat(viewModel.updateInputText(RGBColor.RED, "text")).isNotNull()
    }

    @Test
    fun updateInputText_rgbColorAndNumValue_errorTextIsNullAndSettingTextRGB() {
        // RGBColorと数値の文字列を設定したときにErrorTextがnullかつUiStateのColorDataが設定されることを確認
        assertThat(viewModel.uiState.value.textRGB.red).isEqualTo("")
        val newRedValue = "50"
        assertThat(viewModel.updateInputText(RGBColor.RED, newRedValue)).isNull()
        assertThat(viewModel.uiState.value.textRGB.red).isEqualTo(newRedValue)
    }

    @Test
    fun updateInputText_rgbColorAndNumValue_settingColorData() {
        // RGBColorと数値の文字列を設定したときにUiStateのColorDataが設定されることを確認
        val originRedValue = viewModel.uiState.value.colorData.rgb.red
        val newRedValue = (RGBColor.RED.maxValue - originRedValue).toString()
        viewModel.updateInputText(RGBColor.RED, newRedValue)
        assertThat(viewModel.uiState.value.colorData.rgb.red.toString()).isEqualTo(newRedValue)
    }

    @Test
    fun updateInputText_cmykColorAndNotNumValue_errorTextIsNotNull() {
        // CMYKColorと数値以外の文字列を設定したときにUiStateのErrorTextが設定されることを確認
        assertThat(viewModel.updateInputText(CMYKColor.CYAN, "text")).isNotNull()
    }

    @Test
    fun updateInputText_cmykColorAndNumValue_errorTextIsNullAndSettingTextCMYK() {
        // CMYKColorと数値の文字列を設定したときにErrorTextがnullかつUiStateのColorDataが設定されることを確認
        assertThat(viewModel.uiState.value.textCMYK.cyan).isEqualTo("")
        val newCyanValue = "50"
        assertThat(viewModel.updateInputText(CMYKColor.CYAN, newCyanValue)).isNull()
        assertThat(viewModel.uiState.value.textCMYK.cyan).isEqualTo(newCyanValue)
    }

    @Test
    fun updateInputText_cmykColorAndNumValue_settingColorData() {
        // CMYKColorと数値の文字列を設定したときにUiStateのColorDataが設定されることを確認
        val originCyanValue = viewModel.uiState.value.colorData.cmyk.cyan
        val newCyanValue = (CMYKColor.CYAN.maxValue - originCyanValue).toString()
        viewModel.updateInputText(CMYKColor.CYAN, newCyanValue)
        assertThat(viewModel.uiState.value.colorData.cmyk.cyan.toString()).isEqualTo(newCyanValue)
    }

    @Test
    fun updateInputText_hsvColorAndNotNumValue_errorTextIsNotNull() {
        // HSVColorと数値以外の文字列を設定したときにUiStateのErrorTextが設定されることを確認
        assertThat(viewModel.updateInputText(HSVColor.HUE, "text")).isNotNull()
    }

    @Test
    fun updateInputText_hsvColorAndNumValue_errorTextIsNullAndSettingTextHSV() {
        // HSVColorと数値の文字列を設定したときにErrorTextがnullかつUiStateのColorDataが設定されることを確認
        assertThat(viewModel.uiState.value.textHSV.hue).isEqualTo("")
        val newHueValue = "50"
        assertThat(viewModel.updateInputText(HSVColor.HUE, newHueValue)).isNull()
        assertThat(viewModel.uiState.value.textHSV.hue).isEqualTo(newHueValue)
    }

    @Test
    fun updateInputText_hsvColorAndNumValue_settingColorData() {
        // HSVColorと数値の文字列を設定したときにUiStateのColorDataが設定されることを確認
        val originHueValue = viewModel.uiState.value.colorData.hsv.hue
        val newHueValue = (HSVColor.HUE.maxValue - originHueValue).toString()
        viewModel.updateInputText(HSVColor.HUE, newHueValue)
        assertThat(viewModel.uiState.value.colorData.hsv.hue.toString()).isEqualTo(newHueValue)
    }

    @Test
    fun updateInputText_hexColorIsInvalid_errorTextIsNotNull() {
        // HEXColorと不正な文字列を設定したときにUiStateのErrorTextが設定されることを確認
        assertThat(viewModel.updateInputText("TEST")).isNotNull()
    }

    @Test
    fun updateInputText_hexColor_errorTextIsNullAndSettingHEX() {
        // HEXColorとRGBの文字列を設定したときにErrorTextがnullかつUiStateのColorDataが設定されることを確認
        assertThat(viewModel.uiState.value.hex).isEqualTo("")
        val newHEX = "FFFFFF"
        assertThat(viewModel.updateInputText(newHEX)).isNull()
        assertThat(viewModel.uiState.value.hex).isEqualTo(newHEX)
    }

    @Test
    fun updateInputText_hexColor_settingColorData() {
        // HEXColorとRGBの文字列を設定したときにUiStateのColorDataが設定されることを確認
        val newHEX = "FFFFFF"
        val newRGB = hexToRGB(hex = newHEX)
        viewModel.updateInputText(newHEX)
        assertThat(viewModel.uiState.value.colorData.rgb.red).isEqualTo(newRGB.red)
    }



}