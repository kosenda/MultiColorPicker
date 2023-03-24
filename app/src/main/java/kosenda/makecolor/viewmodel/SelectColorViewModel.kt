package kosenda.makecolor.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.di.IODispatcher
import kosenda.makecolor.model.ColorItem
import kosenda.makecolor.model.default.DefaultCategory
import kosenda.makecolor.model.default.copicColor
import kosenda.makecolor.model.default.jisIdiomaticColor
import kosenda.makecolor.model.default.jisSafetyColor
import kosenda.makecolor.model.default.webColor
import kosenda.makecolor.model.default.x11Color
import kosenda.makecolor.model.repository.ColorRepository
import kosenda.makecolor.view.code.ColorIndex
import kosenda.makecolor.view.state.SelectColorUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

abstract class SelectColorViewModel : ViewModel() {
    abstract val uiState: StateFlow<SelectColorUiState>
    abstract fun getColors()
    abstract fun deleteColorItem(deleteColorItem: ColorItem)
}

@HiltViewModel
class SelectColorViewModelImpl @Inject constructor(
    @IODispatcher val ioDispatcher: CoroutineDispatcher,
    private val colorRepository: ColorRepository,
    savedStateHandle: SavedStateHandle,
) : SelectColorViewModel() {
    private val _uiState = MutableStateFlow(SelectColorUiState())
    override val uiState: StateFlow<SelectColorUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                category = Json.decodeFromString(savedStateHandle.get<String>("category")!!),
                index = savedStateHandle.get<Int>("index") ?: ColorIndex.FIRST.num,
                needBack = savedStateHandle.get<Boolean>("needBack") ?: true,
            )
        }
    }

    override fun getColors() {
        CoroutineScope(ioDispatcher).launch {
            _uiState.update {
                it.copy(
                    colors = when (it.category.name) {
                        DefaultCategory.X11.name -> x11Color
                        DefaultCategory.WEB.name -> webColor
                        DefaultCategory.COPIC.name -> copicColor
                        DefaultCategory.JIS_IDIOMATIC.name -> jisIdiomaticColor
                        DefaultCategory.JIS_SAFETY.name -> jisSafetyColor
                        else -> colorRepository.getColors(category = it.category.name)
                    },
                )
            }
        }
    }

    override fun deleteColorItem(deleteColorItem: ColorItem) {
        CoroutineScope(ioDispatcher).launch {
            colorRepository.deleteColor(colorItem = deleteColorItem)
            _uiState.update {
                it.copy(
                    colors = it.colors.toMutableList().also { mutableColors ->
                        mutableColors.removeIf { colorItem -> colorItem.id == deleteColorItem.id }
                    },
                )
            }
        }
    }
}
