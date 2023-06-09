package kosenda.makecolor.feature.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.core.domain.UpdateColorDataUseCase
import kosenda.makecolor.core.model.data.CMYKColor
import kosenda.makecolor.core.model.data.ColorType
import kosenda.makecolor.core.model.data.HSVColor
import kosenda.makecolor.core.model.data.RGBColor
import kosenda.makecolor.core.ui.state.SeekbarUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

abstract class SeekbarViewModel : ViewModel() {
    abstract val uiState: StateFlow<SeekbarUiState>
    abstract fun updateSelectColorType(index: Int)
    abstract fun updateColorData(rgbType: RGBColor, value: Float)
    abstract fun updateColorData(cmykType: CMYKColor, value: Float)
    abstract fun updateColorData(hsvType: HSVColor, value: Float)
}

@HiltViewModel
class SeekbarViewModelImpl @Inject constructor(
    val updateColorDataUseCase: UpdateColorDataUseCase,
) : SeekbarViewModel() {
    private val _uiState = MutableStateFlow(SeekbarUiState())
    override val uiState: StateFlow<SeekbarUiState> = _uiState.asStateFlow()

    override fun updateSelectColorType(index: Int) {
        val newColorType = when (index) {
            ColorType.RGB.index -> ColorType.RGB
            ColorType.CMYK.index -> ColorType.CMYK
            ColorType.HSV.index -> ColorType.HSV
            else -> throw IllegalArgumentException()
        }
        _uiState.update { it.copy(selectColorType = newColorType) }
    }

    override fun updateColorData(rgbType: RGBColor, value: Float) {
        _uiState.update {
            it.copy(colorData = updateColorDataUseCase(it.colorData, rgbType, value))
        }
    }

    override fun updateColorData(cmykType: CMYKColor, value: Float) {
        _uiState.update {
            it.copy(colorData = updateColorDataUseCase(it.colorData, cmykType, value))
        }
    }

    override fun updateColorData(hsvType: HSVColor, value: Float) {
        _uiState.update {
            it.copy(colorData = updateColorDataUseCase(it.colorData, hsvType, value))
        }
    }
}

class PreviewSeekbarViewModel : SeekbarViewModel() {
    override val uiState: StateFlow<SeekbarUiState> = MutableStateFlow(SeekbarUiState())
    override fun updateSelectColorType(index: Int) {}
    override fun updateColorData(rgbType: RGBColor, value: Float) {}
    override fun updateColorData(cmykType: CMYKColor, value: Float) {}
    override fun updateColorData(hsvType: HSVColor, value: Float) {}
}
