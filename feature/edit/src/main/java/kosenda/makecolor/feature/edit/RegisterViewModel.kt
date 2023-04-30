package kosenda.makecolor.feature.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.core.data.repository.ColorRepository
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.model.data.ColorItem
import kosenda.makecolor.core.ui.data.NavKey
import kosenda.makecolor.core.ui.state.RegisterUiState
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

abstract class RegisterViewModel : ViewModel() {
    abstract val uiState: StateFlow<RegisterUiState>
    abstract fun updateMemo(memo: String)
    abstract fun fetchCategories()
    abstract fun changeSelectCategory(index: Int)
    abstract fun addCategory(newCategory: Category)
    abstract fun closeAddCategoryDialog()
    abstract fun openAddCategoryDialog()
    abstract fun registerColor(hex: String)
    abstract fun clearShowToast()
}

@HiltViewModel
class RegisterViewModelImpl @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val colorRepository: ColorRepository,
    savedStateHandle: SavedStateHandle,
) : RegisterViewModel() {
    private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState())
    override val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                colorData = Json.decodeFromString(
                    savedStateHandle.get<String>(NavKey.COLOR_DATA.key)!!),
            )
        }
    }

    override fun updateMemo(memo: String) {
        _uiState.update { it.copy(memo = memo) }
    }

    override fun fetchCategories() {
        CoroutineScope(ioDispatcher).launch {
            _uiState.update { it.copy(categories = colorRepository.getCategory()) }
            _uiState.update { it.copy(selectCategory = it.categories.first()) }
            val displayCategories = emptyList<String>().toMutableList()
            _uiState.value.categories.map { displayCategories += it.name }
            _uiState.update { it.copy(displayCategories = displayCategories) }
        }
    }

    override fun changeSelectCategory(index: Int) {
        _uiState.update { it.copy(selectCategory = it.categories[index]) }
    }

    override fun addCategory(newCategory: Category) {
        CoroutineScope(ioDispatcher).launch {
            colorRepository.insertCategory(category = newCategory)
            _uiState.update { it.copy(selectCategory = newCategory) }
            _uiState.update { it.copy(categories = colorRepository.getCategory()) }
            val displayCategories = emptyList<String>().toMutableList()
            _uiState.value.categories.map { displayCategories += it.name }
            _uiState.update { it.copy(displayCategories = displayCategories) }
            closeAddCategoryDialog()
        }
    }

    override fun closeAddCategoryDialog() {
        _uiState.update { it.copy(isShowNewCategoryDialog = false) }
    }

    override fun openAddCategoryDialog() {
        _uiState.update { it.copy(isShowNewCategoryDialog = true) }
    }

    override fun registerColor(hex: String) {
        CoroutineScope(ioDispatcher).launch {
            val colorItem = ColorItem(
                hex = hex,
                category = _uiState.value.selectCategory.name,
                memo = _uiState.value.memo,
            )
            colorRepository.insertColor(color = colorItem)
            uiState.value.selectCategory.name.let {
                colorRepository.updateSize(colorRepository.getSize(it), it)
            }
            _uiState.update { it.copy(showToast = true) }
        }
    }

    override fun clearShowToast() {
        _uiState.update { it.copy(showToast = false) }
    }
}

class PreviewRegisterViewModel : RegisterViewModel() {
    override val uiState: StateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState())
    override fun updateMemo(memo: String) {}
    override fun fetchCategories() {}
    override fun changeSelectCategory(index: Int) {}
    override fun addCategory(newCategory: Category) {}
    override fun closeAddCategoryDialog() {}
    override fun openAddCategoryDialog() {}
    override fun registerColor(hex: String) {}
    override fun clearShowToast() {}
}