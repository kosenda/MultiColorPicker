package kosenda.makecolor.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.R
import kosenda.makecolor.di.IODispatcher
import kosenda.makecolor.di.MainDispatcher
import kosenda.makecolor.model.Category
import kosenda.makecolor.model.data.ColorItem
import kosenda.makecolor.model.repository.ColorRepository
import kosenda.makecolor.view.state.RegisterUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    abstract fun registerColor(hex: String, context: Context)
}

@HiltViewModel
class RegisterViewModelImpl @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val colorRepository: ColorRepository,
    savedStateHandle: SavedStateHandle,
) : RegisterViewModel() {
    private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState())
    override val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                colorData = Json.decodeFromString(savedStateHandle.get<String>("colorData")!!),
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

    override fun registerColor(hex: String, context: Context) {
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
            withContext(mainDispatcher) {
                Toast.makeText(
                    context,
                    R.string.registrated,
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }
}
