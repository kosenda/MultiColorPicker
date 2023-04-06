package kosenda.makecolor.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.di.IODispatcher
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.model.usecase.FetchCategoriesUseCase
import kosenda.makecolor.view.state.DataUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class DataViewModel : ViewModel() {
    abstract val uiState: StateFlow<DataUiState>
    abstract fun fetchCategories(defaultCategories: List<Category>)
}

@HiltViewModel
class DataViewModelImpl @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    val fetchCategoriesUseCase: FetchCategoriesUseCase,
) : DataViewModel() {
    private val _uiState = MutableStateFlow(DataUiState())
    override val uiState: StateFlow<DataUiState> = _uiState.asStateFlow()

    override fun fetchCategories(defaultCategories: List<Category>) {
        CoroutineScope(ioDispatcher).launch {
            _uiState.update {
                it.copy(categories = fetchCategoriesUseCase(defaultCategories))
            }
        }
    }
}
