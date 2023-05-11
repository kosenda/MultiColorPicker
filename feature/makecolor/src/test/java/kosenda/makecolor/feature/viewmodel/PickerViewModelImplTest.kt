package kosenda.makecolor.feature.viewmodel

import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.mock.MockHsvColor
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.ui.data.PickerType
import kosenda.makecolor.core.util.hsvColorToHsv
import kosenda.makecolor.core.util.hsvToRGB
import kosenda.makecolor.core.util.rgbToCmyk
import kosenda.makecolor.core.util.rgbToHex
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PickerViewModelImplTest {

    private val viewModel = PickerViewModelImpl()

    @Test
    fun updateColorData_hsvColor_updatedUiState() {
        // hsvColorを渡したときにColorDataが作成されUiStateに反映されることを確認する
        val hsv = hsvColorToHsv(hsvColor = MockHsvColor().item)
        val rgb = hsvToRGB(hsv = hsv)
        val cmyk = rgbToCmyk(rgb)
        val hex = rgbToHex(rgb)
        viewModel.updateColorData(MockHsvColor().item)
        assertThat(viewModel.uiState.value.colorData).isEqualTo(ColorData(rgb, cmyk, hsv, hex))
    }

    @Test
    fun updateHsvColor_hsvColor_updatedUiState() {
        // hsvColorを渡したときにUiStateに反映されることを確認する
        val originColorData = viewModel.uiState.value.colorData
        val originHsvColorData = viewModel.uiState.value.hsvColorData
        viewModel.updateHsvColor(MockHsvColor().item)
        assertThat(viewModel.uiState.value.colorData).isNotEqualTo(originColorData)
        assertThat(viewModel.uiState.value.hsvColorData).isNotEqualTo(originHsvColorData)
    }

    @Test
    fun updatePickerType_pickerType_updatedUiState() {
        // pickerTypeを渡したときにUiStateに反映されることを確認する
        viewModel.updatePickerType(PickerType.CIRCLE)
        assertThat(viewModel.uiState.value.pickerType).isEqualTo(PickerType.CIRCLE)
        viewModel.updatePickerType(PickerType.SQUARE)
        assertThat(viewModel.uiState.value.pickerType).isEqualTo(PickerType.SQUARE)
    }

    @Test
    fun updateHarmonyMode_colorHarmonyMode_updatedUiState() {
        // colorHarmonyModeを渡したときにUiStateに反映されることを確認する
        viewModel.updateHarmonyMode(ColorHarmonyMode.COMPLEMENTARY)
        assertThat(viewModel.uiState.value.harmonyMode).isEqualTo(ColorHarmonyMode.COMPLEMENTARY)
        viewModel.updateHarmonyMode(ColorHarmonyMode.ANALOGOUS)
        assertThat(viewModel.uiState.value.harmonyMode).isEqualTo(ColorHarmonyMode.ANALOGOUS)
    }
}