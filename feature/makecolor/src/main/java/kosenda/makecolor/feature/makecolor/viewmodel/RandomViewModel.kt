package kosenda.makecolor.feature.makecolor.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.core.domain.UpdateOtherColorUseCase
import kosenda.makecolor.core.model.data.ColorType
import kosenda.makecolor.core.model.data.RandomType
import kosenda.makecolor.core.ui.state.RandomUiState
import kosenda.makecolor.core.util.colorToRGB
import kosenda.makecolor.core.util.outputRandomRGBColors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

abstract class RandomViewModel : ViewModel() {
    abstract val uiState: StateFlow<RandomUiState>
    abstract fun updateColorData(color: Color)
    abstract fun updateRandomType(index: Int)
    abstract fun outputRandomColors()
}

@HiltViewModel
class RandomViewModelImpl @Inject constructor(
    private val updateOtherColorUseCase: UpdateOtherColorUseCase,
) : RandomViewModel() {
    private val _uiState = MutableStateFlow(RandomUiState())
    override val uiState: StateFlow<RandomUiState> = _uiState.asStateFlow()

    override fun updateColorData(color: Color) {
        _uiState.update {
            it.copy(
                colorData = updateOtherColorUseCase(
                    colorData = it.colorData.copy(rgb = colorToRGB(color = color)),
                    type = ColorType.RGB,
                ),
            )
        }
    }

    override fun updateRandomType(index: Int) {
        _uiState.update {
            it.copy(
                selectRandomType = when (index) {
                    RandomType.NOT_SPECIFIED.index -> RandomType.NOT_SPECIFIED
                    RandomType.VIVID.index -> RandomType.VIVID
                    RandomType.PASTEL.index -> RandomType.PASTEL
                    RandomType.BLACK_AND_WHITE.index -> RandomType.BLACK_AND_WHITE
                    else -> throw IllegalArgumentException()
                },
            )
        }
    }

    override fun outputRandomColors() {
        _uiState.update {
            it.copy(randomRGBColors = outputRandomRGBColors(randomType = it.selectRandomType))
        }
    }
}

class PreviewRandomViewModel : RandomViewModel() {
    override val uiState: StateFlow<RandomUiState> = MutableStateFlow(RandomUiState())
    override fun updateColorData(color: Color) {}
    override fun updateRandomType(index: Int) {}
    override fun outputRandomColors() {}
}