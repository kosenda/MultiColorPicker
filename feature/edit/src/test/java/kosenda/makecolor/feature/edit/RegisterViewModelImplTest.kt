package kosenda.makecolor.feature.edit

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import io.mockk.spyk
import io.mockk.verify
import kosenda.makecolor.core.mock.FakeColorRepository
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.testing.MainDispatcherRule
import kosenda.makecolor.core.ui.data.NavKey
import kosenda.makecolor.core.util.randomColorData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class RegisterViewModelImplTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val registerViewModel = spyk(
        RegisterViewModelImpl(
            ioDispatcher = mainDispatcherRule.testDispatcher,
            mainDispatcher = mainDispatcherRule.testDispatcher,
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
        assertThat(registerViewModel.uiState.value.categories.size).isEqualTo(2)
        assertThat(registerViewModel.uiState.value.selectCategory)
            .isEqualTo(Category("test1", 0))
        assertThat(registerViewModel.uiState.value.displayCategories.size).isEqualTo(2)
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
        assertThat(registerViewModel.uiState.value.categories.size).isEqualTo(2)
        registerViewModel.addCategory(newCategory = Category(name = "test", size = 0))
        assertThat(registerViewModel.uiState.value.categories.size).isEqualTo(3)
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

    @Ignore("TODO: Toastを表示するのにContextを使用しているため今後削除する。")
    @Test
    fun registerColor_add1_isRegistered() = runTest {
        // カラーが登録されてサイズが更新されていることを確認
        registerViewModel.fetchCategories()
        assertThat(registerViewModel.uiState.value.selectCategory.size).isEqualTo(0)
        assertThat(registerViewModel.uiState.value.categories.first().size).isEqualTo(0)
        registerViewModel.registerColor(hex = "FFFFFF", context = context)
        registerViewModel.fetchCategories()
        assertThat(registerViewModel.uiState.value.selectCategory.size).isEqualTo(1)
        assertThat(registerViewModel.uiState.value.categories.first().size).isEqualTo(1)
    }
}

