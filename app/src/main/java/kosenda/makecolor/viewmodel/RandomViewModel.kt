package kosenda.makecolor.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.model.ColorType
import kosenda.makecolor.model.usecase.UpdateOtherColorUseCase
import kosenda.makecolor.model.util.colorToRGB
import kosenda.makecolor.model.util.outputRandomRGBColors
import kosenda.makecolor.view.code.RandomType
import kosenda.makecolor.view.state.RandomUiState
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
                    RandomType.Vivid.index -> RandomType.Vivid
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
