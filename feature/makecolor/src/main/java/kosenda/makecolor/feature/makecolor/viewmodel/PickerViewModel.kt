package kosenda.makecolor.feature.makecolor.viewmodel

import androidx.lifecycle.ViewModel
import com.godaddy.android.colorpicker.HsvColor
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.core.ui.code.PickerType
import kosenda.makecolor.core.ui.state.PickerUiState
import kosenda.makecolor.core.util.hsvColorToHsv
import kosenda.makecolor.core.util.hsvToRGB
import kosenda.makecolor.core.util.rgbToCmyk
import kosenda.makecolor.core.util.rgbToColor
import kosenda.makecolor.core.util.rgbToHex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

abstract class PickerViewModel : ViewModel() {
    abstract val uiState: StateFlow<PickerUiState>
    abstract fun updateHsvColor(color: HsvColor)
    abstract fun updateColorData(color: HsvColor)
    abstract fun updatePickerType(type: PickerType)
    abstract fun updateHarmonyMode(mode: ColorHarmonyMode)
}

@HiltViewModel
class PickerViewModelImpl @Inject constructor() : PickerViewModel() {
    private val _uiState = MutableStateFlow(PickerUiState())
    override val uiState: StateFlow<PickerUiState> = _uiState.asStateFlow()

    init {
        val mainColor = HsvColor.from(color = rgbToColor(uiState.value.colorData.rgb))
        val subColors = mainColor.getColors(colorHarmonyMode = ColorHarmonyMode.ANALOGOUS)
        _uiState.update {
            it.copy(
                defaultColor = rgbToColor(uiState.value.colorData.rgb),
                hsvColorData = it.hsvColorData.copy(
                    hsv1 = mainColor,
                    hsv2 = subColors[0],
                    hsv3 = subColors[1],
                    hsv4 = subColors[2],
                    hsv5 = subColors[3],
                ),
            )
        }
    }

    override fun updateHsvColor(color: HsvColor) {
        val subColors = color.getColors(colorHarmonyMode = _uiState.value.harmonyMode)
        _uiState.update {
            it.copy(
                hsvColorData = it.hsvColorData.copy(
                    hsv1 = color,
                    hsv2 = subColors[0],
                    hsv3 = subColors[1],
                    hsv4 = subColors[2],
                    hsv5 = subColors[3],
                ),
            )
        }
        updateColorData(color = color)
    }

    override fun updateColorData(color: HsvColor) {
        val hsv = hsvColorToHsv(hsvColor = color)
        val rgb = hsvToRGB(hsv = hsv)
        _uiState.update {
            it.copy(
                colorData = it.colorData.copy(
                    hsv = hsv,
                    rgb = rgb,
                    cmyk = rgbToCmyk(rgb),
                    hex = rgbToHex(rgb),
                ),
            )
        }
    }

    override fun updatePickerType(type: PickerType) {
        _uiState.update { it.copy(pickerType = type) }
    }

    override fun updateHarmonyMode(mode: ColorHarmonyMode) {
        _uiState.update { it.copy(harmonyMode = mode) }
    }
}

class PreviewPickerViewModel : PickerViewModel() {
    override val uiState: StateFlow<PickerUiState> = MutableStateFlow(PickerUiState())
    override fun updateHsvColor(color: HsvColor) {}
    override fun updateColorData(color: HsvColor) {}
    override fun updatePickerType(type: PickerType) {}
    override fun updateHarmonyMode(mode: ColorHarmonyMode) {}
}