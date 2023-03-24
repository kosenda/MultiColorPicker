package kosenda.makecolor.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.model.ColorType
import kosenda.makecolor.model.usecase.UpdateColorDataUseCase
import kosenda.makecolor.view.CMYKColor
import kosenda.makecolor.view.HSVColor
import kosenda.makecolor.view.RGBColor
import kosenda.makecolor.view.state.SeekbarUiState
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
