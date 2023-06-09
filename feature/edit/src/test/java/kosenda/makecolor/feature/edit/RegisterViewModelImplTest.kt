package kosenda.makecolor.feature.edit

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.spyk
import io.mockk.verify
import kosenda.makecolor.core.mock.FakeColorRepository
import kosenda.makecolor.core.mock.MockCategory
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.testing.MainDispatcherRule
import kosenda.makecolor.core.ui.data.NavKey
import kosenda.makecolor.core.util.randomColorData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterViewModelImplTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val registerViewModel = spyk(
        RegisterViewModelImpl(
            ioDispatcher = mainDispatcherRule.testDispatcher,
            colorRepository = FakeColorRepository(),
            savedStateHandle = SavedStateHandle(
                mapOf(NavKey.COLOR_DATA.key to Json.encodeToString(randomColorData())),
            ),
        ),
    )

    @Test
    fun updateMemo_text_isChanged() {
        // メモが変更されていることを確認
        assertThat(registerViewModel.uiState.value.memo).isEqualTo("")
        registerViewModel.updateMemo("test")
        assertThat(registerViewModel.uiState.value.memo).isEqualTo("test")
    }

    @Test
    fun fetchCategories_initial_isChangeUiState() = runTest {
        // UiStateが変更されることを確認
        assertThat(registerViewModel.uiState.value.categories).isEmpty()
        assertThat(registerViewModel.uiState.value.selectCategory)
            .isEqualTo(Category("Category1", 0))
        assertThat(registerViewModel.uiState.value.displayCategories).isEmpty()
        registerViewModel.fetchCategories()
        assertThat(registerViewModel.uiState.value.categories.size)
            .isEqualTo(MockCategory().list.size)
        assertThat(registerViewModel.uiState.value.selectCategory)
            .isEqualTo(MockCategory().list.first())
        assertThat(registerViewModel.uiState.value.displayCategories.size)
            .isEqualTo(MockCategory().list.size)
    }

    @Test
    fun changeSelectCategory_add_isChangeable() = runTest {
        // 選択されているカテゴリーが変更されることを確認
        assertThat(registerViewModel.uiState.value.selectCategory)
            .isEqualTo(Category("Category1", 0))
        registerViewModel.addCategory(newCategory = Category(name = "test", size = 0))
        registerViewModel.changeSelectCategory(index = 1)
        assertThat(registerViewModel.uiState.value.selectCategory)
            .isNotEqualTo(Category("Category1", 0))
    }

    @Test
    fun addCategory_add_isChangeable() = runTest {
        // カテゴリーが追加されること、closeAddCategoryDialogが呼ばれることを確認
        registerViewModel.fetchCategories()
        assertThat(registerViewModel.uiState.value.categories.size)
            .isEqualTo(MockCategory().list.size)
        registerViewModel.addCategory(newCategory = Category(name = "add", size = 0))
        assertThat(registerViewModel.uiState.value.categories.size)
            .isEqualTo(MockCategory().list.size + 1)
        verify { registerViewModel.closeAddCategoryDialog() }
    }

    @Test
    fun closeAddCategoryDialog_once_isClosed() {
        // isShowNewCategoryDialogがfalseになることを確認
        assertThat(registerViewModel.uiState.value.isShowNewCategoryDialog).isFalse()
        registerViewModel.openAddCategoryDialog()
        assertThat(registerViewModel.uiState.value.isShowNewCategoryDialog).isTrue()
        registerViewModel.closeAddCategoryDialog()
        assertThat(registerViewModel.uiState.value.isShowNewCategoryDialog).isFalse()
    }

    @Test
    fun openAddCategoryDialog_once_isShowed() {
        // isShowNewCategoryDialogがtrueになることを確認
        registerViewModel.closeAddCategoryDialog()
        assertThat(registerViewModel.uiState.value.isShowNewCategoryDialog).isFalse()
        registerViewModel.openAddCategoryDialog()
        assertThat(registerViewModel.uiState.value.isShowNewCategoryDialog).isTrue()
    }

    @Test
    fun registerColor_add1_isRegistered() = runTest {
        // カラーが登録されてサイズが更新されていることを確認
        registerViewModel.fetchCategories()
        assertThat(registerViewModel.uiState.value.selectCategory.size).isEqualTo(0)
        assertThat(registerViewModel.uiState.value.categories.first().size).isEqualTo(0)
        registerViewModel.registerColor(hex = "FFFFFF")
        registerViewModel.fetchCategories()
        assertThat(registerViewModel.uiState.value.selectCategory.size).isEqualTo(1)
        assertThat(registerViewModel.uiState.value.categories.first().size).isEqualTo(1)
        assertThat(registerViewModel.uiState.value.showToast).isTrue()
    }

    @Test
    fun clearShowToast_once_isFalse() {
        // isShowToastがfalseになることを確認
        registerViewModel.registerColor(hex = "FFFFFF")
        assertThat(registerViewModel.uiState.value.showToast).isTrue()
        registerViewModel.clearShowToast()
        assertThat(registerViewModel.uiState.value.showToast).isFalse()
    }
}

