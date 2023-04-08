package kosenda.makecolor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.core.domain.FetchCategoriesUseCase
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.ui.code.ColorIndex
import kosenda.makecolor.core.ui.code.SplitColorNum
import kosenda.makecolor.core.ui.state.SplitUiState
import kosenda.makecolor.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class SplitViewModel : ViewModel() {
    abstract val uiState: StateFlow<SplitUiState>
    abstract fun init(navBackStackEntry: NavBackStackEntry?)
    abstract fun fetchCategories(defaultCategories: List<Category>)
    abstract fun updateSelectSplitColorNum(index: Int)
    abstract fun updateSelectCategory(categoryIndex: Int, colorIndex: ColorIndex)
}

@HiltViewModel
class SplitViewModelImpl @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    val fetchCategoriesUseCase: FetchCategoriesUseCase,
) : SplitViewModel() {
    private val _uiState = MutableStateFlow(SplitUiState())
    override val uiState: StateFlow<SplitUiState> = _uiState.asStateFlow()

    override fun init(navBackStackEntry: NavBackStackEntry?) {
        val savedStateHandle = navBackStackEntry?.savedStateHandle ?: return
        savedStateHandle.run {
            val newHex = get<String>(key = "newHex") ?: ""
            val index = get<Int>(key = "index")
            if (newHex.isNotEmpty() || index != null) {
                when (index) {
                    ColorIndex.FIRST.num -> _uiState.update { it.copy(selectHex1 = newHex) }
                    ColorIndex.SECOND.num -> _uiState.update { it.copy(selectHex2 = newHex) }
                    ColorIndex.THIRD.num -> _uiState.update { it.copy(selectHex3 = newHex) }
                    ColorIndex.FOURTH.num -> _uiState.update { it.copy(selectHex4 = newHex) }
                }
            }
        }
    }

    override fun fetchCategories(defaultCategories: List<Category>) {
        if (uiState.value.categories.isEmpty()) {
            CoroutineScope(ioDispatcher).launch {
                val categories = fetchCategoriesUseCase(defaultCategories)
                _uiState.update {
                    it.copy(
                        categories = defaultCategories,
                        selectCategory1 = categories.first(),
                        selectCategory2 = categories.first(),
                        selectCategory3 = categories.first(),
                        selectCategory4 = categories.first(),
                    )
                }
            }
        }
    }

    override fun updateSelectSplitColorNum(index: Int) {
        _uiState.update {
            it.copy(
                selectSplitColorNum = when (index) {
                    SplitColorNum.TWO.index -> SplitColorNum.TWO
                    SplitColorNum.THREE.index -> SplitColorNum.THREE
                    SplitColorNum.FOUR.index -> SplitColorNum.FOUR
                    else -> throw IllegalArgumentException("Undefined value: $index")
                },
            )
        }
    }

    override fun updateSelectCategory(categoryIndex: Int, colorIndex: ColorIndex) {
        _uiState.update {
            val category = it.categories[categoryIndex]
            when (colorIndex) {
                ColorIndex.FIRST -> it.copy(selectCategory1 = category)
                ColorIndex.SECOND -> it.copy(selectCategory2 = category)
                ColorIndex.THIRD -> it.copy(selectCategory3 = category)
                ColorIndex.FOURTH -> it.copy(selectCategory4 = category)
            }
        }
    }
}
