package kosenda.makecolor.feature.display.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.core.data.repository.ColorRepository
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.model.data.CategoryDetailParam
import kosenda.makecolor.core.ui.data.NavKey
import kosenda.makecolor.core.ui.state.CategoryDetailUiState
import kosenda.makecolor.core.util.IODispatcher
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

abstract class CategoryDetailViewModel : ViewModel() {
    abstract val uiState: StateFlow<CategoryDetailUiState>
    abstract fun updateCategory(newCategory: Category)
    abstract fun deleteCategory()
    abstract fun closeNewCategoryDialog()
    abstract fun openNewCategoryDialog()
}

@HiltViewModel
class CategoryDetailViewModelImpl @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val colorRepository: ColorRepository,
    savedStateHandle: SavedStateHandle,
) : CategoryDetailViewModel() {
    private val _uiState = MutableStateFlow(CategoryDetailUiState())
    override val uiState: StateFlow<CategoryDetailUiState> = _uiState.asStateFlow()

    init {
        val categoryDetailParam = Json.decodeFromString<CategoryDetailParam>(
            string = savedStateHandle.get<String>(NavKey.CATEGORY_DETAIL.key)!!,
        )
        _uiState.update {
            it.copy(
                colors = categoryDetailParam.colors,
                category = categoryDetailParam.category,
                isDefault = categoryDetailParam.isDefault,
            )
        }
    }

    override fun updateCategory(newCategory: Category) {
        CoroutineScope(ioDispatcher).launch {
            colorRepository.updateCategory(
                oldName = uiState.value.category.name,
                newName = newCategory.name,
            )
            closeNewCategoryDialog()
        }
    }

    override fun deleteCategory() {
        CoroutineScope(ioDispatcher).launch {
            colorRepository.deleteCategory(name = uiState.value.category.name)
            closeNewCategoryDialog()
        }
    }

    override fun closeNewCategoryDialog() {
        _uiState.update { it.copy(isShowNewCategoryDialog = false) }
    }

    override fun openNewCategoryDialog() {
        _uiState.update { it.copy(isShowNewCategoryDialog = true) }
    }
}

class PreviewCategoryDetailViewModel : CategoryDetailViewModel() {
    override val uiState: StateFlow<CategoryDetailUiState> =
        MutableStateFlow(CategoryDetailUiState())
    override fun updateCategory(newCategory: Category) {}
    override fun deleteCategory() {}
    override fun closeNewCategoryDialog() {}
    override fun openNewCategoryDialog() {}
}
