package kosenda.makecolor.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.core.model.data.ColorItem
import kosenda.makecolor.model.util.hexToRGB
import kosenda.makecolor.model.util.rgbToColorData
import kosenda.makecolor.model.util.rgbToComplementaryRgb
import kosenda.makecolor.model.util.rgbToOppositeRgb
import kosenda.makecolor.view.state.ColorDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

abstract class ColorDetailViewModel : ViewModel() {
    abstract val uiState: StateFlow<ColorDetailUiState>
}

@HiltViewModel
class ColorDetailViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ColorDetailViewModel() {
    private val _uiState = MutableStateFlow(ColorDetailUiState())
    override val uiState: StateFlow<ColorDetailUiState> = _uiState.asStateFlow()

    init {
        val colorItem = Json.decodeFromString<ColorItem>(
            string = savedStateHandle.get<String>("colorItem")!!,
        )
        _uiState.update {
            it.copy(
                memo = colorItem.memo,
                categoryName = savedStateHandle.get<String>("categoryName") ?: "",
                complementaryColorData = rgbToColorData(
                    rgb = rgbToComplementaryRgb(rgb = hexToRGB(colorItem.hex)),
                ),
                oppositeColorData = rgbToColorData(
                    rgb = rgbToOppositeRgb(rgb = hexToRGB(colorItem.hex)),
                ),
            )
        }
    }
}
