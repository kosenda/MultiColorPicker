package kosenda.makecolor.feature.edit

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.data.repository.ColorRepository
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.model.data.ColorItem
import kosenda.makecolor.core.testing.MainDispatcherRule
import kosenda.makecolor.core.ui.data.NavKey
import kosenda.makecolor.core.util.convertDisplayStringListFromCategories
import kosenda.makecolor.core.util.randomColorData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelImplTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val initialColorDataJson = Json.encodeToString(randomColorData())
    private val registerViewModel = RegisterViewModelImpl(
        ioDispatcher = mainDispatcherRule.testDispatcher,
        mainDispatcher = mainDispatcherRule.testDispatcher,
        colorRepository = FakeColorRepository(),
        savedStateHandle = SavedStateHandle(mapOf(NavKey.COLOR_DATA.key to initialColorDataJson)),
    )

    @Test
    fun updateMemo_text_isChanged() {
        // メモが変更されていることを確認
        assertThat(registerViewModel.uiState.value.memo).isEqualTo("")
        registerViewModel.updateMemo("test")
        assertThat(registerViewModel.uiState.value.memo).isEqualTo("test")
    }

    @Test
    fun fetchCategories_initial_isChangeUiState() {
        // UiStateが変更されることを確認
        assertThat(registerViewModel.uiState.value.categories).isEmpty()
        assertThat(registerViewModel.uiState.value.selectCategory)
            .isEqualTo(Category("Category1", 0))
        assertThat(registerViewModel.uiState.value.displayCategories).isEmpty()
        registerViewModel.fetchCategories()
        assertThat(registerViewModel.uiState.value.categories.size).isEqualTo(2)
        assertThat(registerViewModel.uiState.value.selectCategory)
            .isEqualTo(Category("test1", 0))
        assertThat(registerViewModel.uiState.value.displayCategories.size).isEqualTo(2)
    }
}

class FakeColorRepository : ColorRepository {

    private val colors = mutableListOf<ColorItem>()
    private val categories = mutableListOf(
        Category("test1", 0),
        Category("test2", 0),
    )

    override suspend fun getColors(category: String): List<ColorItem> {
        return colors.filter { it.category == category }
    }
    override suspend fun getSize(category: String): Int = colors.size
    override suspend fun isExistCategory(category: String): Boolean {
        if (categories.isEmpty()) return false
        convertDisplayStringListFromCategories(categories).forEach {
            if (it == category) return true
        }
        return false
    }
    override suspend fun updateSize(size: Int, name: String) {}
    override suspend fun updateCategory(oldName: String, newName: String) {}
    override suspend fun colorChangeCategory(category: String, newCategory: String) {}
    override suspend fun deleteColor(colorItem: ColorItem) {}
    override suspend fun decreaseSize(name: String) {}
    override suspend fun deleteCategory(name: String) {}
    override suspend fun deleteColors(category: String) {}
    override suspend fun deleteAllCategories() {}
    override suspend fun deleteAllColors() {}
    override suspend fun insertColor(color: ColorItem) {
        colors.add(color)
    }
    override suspend fun getCategory(): List<Category> {
        return categories
    }
    override suspend fun insertCategory(category: Category) {
        categories.add(category)
    }
}