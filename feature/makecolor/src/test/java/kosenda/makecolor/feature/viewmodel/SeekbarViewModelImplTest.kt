package kosenda.makecolor.feature.viewmodel

import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.domain.UpdateColorDataUseCase
import kosenda.makecolor.core.domain.UpdateOtherColorUseCase
import kosenda.makecolor.core.model.data.CMYKColor
import kosenda.makecolor.core.model.data.ColorType
import kosenda.makecolor.core.model.data.HSVColor
import kosenda.makecolor.core.model.data.RGBColor
import kosenda.makecolor.core.util.randomColorData
import org.junit.Assert
import org.junit.Test

class SeekbarViewModelImplTest {

    private val viewModel = SeekbarViewModelImpl(
        updateColorDataUseCase = UpdateColorDataUseCase(
            updateOtherColorUseCase = UpdateOtherColorUseCase()
        ),
    )

    @Test
    fun updateSelectColorType_everyIndex_settingColorType() {
        // Indexを設定したときにUiStateのSelectColorTypeが設定されることを確認
        assertThat(viewModel.uiState.value.selectColorType).isEqualTo(ColorType.RGB)
        viewModel.updateSelectColorType(ColorType.CMYK.index)
        assertThat(viewModel.uiState.value.selectColorType).isEqualTo(ColorType.CMYK)
        viewModel.updateSelectColorType(ColorType.RGB.index)
        assertThat(viewModel.uiState.value.selectColorType).isEqualTo(ColorType.RGB)
        viewModel.updateSelectColorType(ColorType.HSV.index)
        assertThat(viewModel.uiState.value.selectColorType).isEqualTo(ColorType.HSV)
    }

    @Test
    fun updateSelectColorType_undefinedIndex_throwIllegalArgument() {
        // 未定義のIndexを設定したときにIllegalArgumentExceptionが発生することを確認
        Assert.assertThrows(IllegalArgumentException::class.java) {
            viewModel.updateSelectColorType(99)
        }
    }

    @Test
    fun updateColorData_rgbType_settingUiState() {
        // RGBColorを設定したときにUiStateのColorDataが設定されることを確認
        val red = randomColorData().rgb.red
        val green = randomColorData().rgb.green
        val blue = randomColorData().rgb.blue
        viewModel.updateColorData(RGBColor.RED, red)
        assertThat(viewModel.uiState.value.colorData.rgb.red).isEqualTo(red)
        viewModel.updateColorData(RGBColor.GREEN, green)
        assertThat(viewModel.uiState.value.colorData.rgb.green).isEqualTo(green)
        viewModel.updateColorData(RGBColor.BLUE, blue)
        assertThat(viewModel.uiState.value.colorData.rgb.blue).isEqualTo(blue)
    }

    @Test
    fun updateColorData_cmykType_settingUiState() {
        // CMYKColorを設定したときにUiStateのColorDataが設定されることを確認
        val cyan = randomColorData().cmyk.cyan
        val magenta = randomColorData().cmyk.magenta
        val yellow = randomColorData().cmyk.yellow
        val black = randomColorData().cmyk.black
        viewModel.updateColorData(CMYKColor.CYAN, cyan)
        assertThat(viewModel.uiState.value.colorData.cmyk.cyan).isEqualTo(cyan)
        viewModel.updateColorData(CMYKColor.MAGENTA, magenta)
        assertThat(viewModel.uiState.value.colorData.cmyk.magenta).isEqualTo(magenta)
        viewModel.updateColorData(CMYKColor.YELLOW, yellow)
        assertThat(viewModel.uiState.value.colorData.cmyk.yellow).isEqualTo(yellow)
        viewModel.updateColorData(CMYKColor.BLACK, black)
        assertThat(viewModel.uiState.value.colorData.cmyk.black).isEqualTo(black)
    }

    @Test
    fun updateColorData_hsvType_settingUiState() {
        // HSVColorを設定したときにUiStateのColorDataが設定されることを確認
        val hue = randomColorData().hsv.hue
        val saturation = randomColorData().hsv.saturation
        val value = randomColorData().hsv.value
        viewModel.updateColorData(HSVColor.HUE, hue)
        assertThat(viewModel.uiState.value.colorData.hsv.hue).isEqualTo(hue)
        viewModel.updateColorData(HSVColor.SATURATION, saturation)
        assertThat(viewModel.uiState.value.colorData.hsv.saturation).isEqualTo(saturation)
        viewModel.updateColorData(HSVColor.VALUE, value)
        assertThat(viewModel.uiState.value.colorData.hsv.value).isEqualTo(value)
    }
}