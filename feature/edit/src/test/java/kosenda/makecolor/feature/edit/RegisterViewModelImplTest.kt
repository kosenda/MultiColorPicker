package kosenda.makecolor.feature.edit

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.mock.FakeColorRepository
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.testing.MainDispatcherRule
import kosenda.makecolor.core.ui.data.NavKey
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

    @Test
    fun changeSelectCategory_add_isChangeable() {
        // 選択されているカテゴリーが変更されることを確認
        assertThat(registerViewModel.uiState.value.selectCategory)
            .isEqualTo(Category("Category1", 0))
        registerViewModel.addCategory(newCategory = Category(name = "test", size = 0))
        registerViewModel.changeSelectCategory(index = 1)
        assertThat(registerViewModel.uiState.value.selectCategory)
            .isNotEqualTo(Category("Category1", 0))
    }
}

