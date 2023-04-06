package kosenda.makecolor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.di.IODispatcher
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.model.usecase.FetchCategoriesUseCase
import kosenda.makecolor.model.util.convertDisplayStringListFromCategories
import kosenda.makecolor.view.code.ColorIndex
import kosenda.makecolor.view.state.GradationUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class GradationViewModel : ViewModel() {
    abstract val uiState: StateFlow<GradationUiState>
    abstract fun init(navBackStackEntry: NavBackStackEntry?)
    abstract fun fetchCategories(defaultCategories: List<Category>)
    abstract fun updateSelectCategory1(index: Int)
    abstract fun updateSelectCategory2(index: Int)
}

@HiltViewModel
class GradationViewModelImpl @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    val fetchCategoriesUseCase: FetchCategoriesUseCase,
) : GradationViewModel() {
    private val _uiState = MutableStateFlow(GradationUiState())
    override val uiState: StateFlow<GradationUiState> = _uiState.asStateFlow()

    override fun init(navBackStackEntry: NavBackStackEntry?) {
        val savedStateHandle = navBackStackEntry?.savedStateHandle ?: return
        savedStateHandle.run {
            val newHex = get<String>(key = "newHex") ?: ""
            val index = get<Int>(key = "index")
            if (newHex.isNotEmpty() || index != null) {
                when (index) {
                    ColorIndex.FIRST.num -> _uiState.update { it.copy(selectHex1 = newHex) }
                    ColorIndex.SECOND.num -> _uiState.update { it.copy(selectHex2 = newHex) }
                    else -> throw IllegalArgumentException("Missing Index: $index")
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
                        categories = categories,
                        selectCategory1 = categories.first(),
                        selectCategory2 = categories.first(),
                        displayCategories = convertDisplayStringListFromCategories(categories),
                    )
                }
            }
        }
    }

    override fun updateSelectCategory1(index: Int) {
        _uiState.update { it.copy(selectCategory1 = it.categories[index]) }
    }

    override fun updateSelectCategory2(index: Int) {
        _uiState.update { it.copy(selectCategory2 = it.categories[index]) }
    }
}
