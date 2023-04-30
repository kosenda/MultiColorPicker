package kosenda.makecolor.feature.display.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.spyk
import kosenda.makecolor.core.mock.FakeColorRepository
import kosenda.makecolor.core.mock.MockCategory
import kosenda.makecolor.core.mock.MockColorItem
import kosenda.makecolor.core.model.data.CategoryDetailParam
import kosenda.makecolor.core.testing.MainDispatcherRule
import kosenda.makecolor.core.ui.data.NavKey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CategoryDetailViewModelImplTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val colorRepository = FakeColorRepository()
    private val categoryDetailParam = CategoryDetailParam(
        colors = MockColorItem().list,
        category = MockCategory().item,
        isDefault = false,
    )
    private val viewModel = spyk(
        CategoryDetailViewModelImpl(
            ioDispatcher = mainDispatcherRule.testDispatcher,
            colorRepository = colorRepository,
            savedStateHandle = SavedStateHandle(
                mapOf(NavKey.CATEGORY_DETAIL.key to Json.encodeToString(categoryDetailParam)),
            ),
        ),
    )

    @Test
    fun categoryDetailViewModel_init_settingSavedStateHandle() {
        // initでSavedStateHandleに保存されたCategoryDetailParamを取得して設定されることを確認
        assertThat(viewModel.uiState.value.colors).isEqualTo(categoryDetailParam.colors)
        assertThat(viewModel.uiState.value.category).isEqualTo(categoryDetailParam.category)
        assertThat(viewModel.uiState.value.isDefault).isEqualTo(categoryDetailParam.isDefault)
    }

    @Test
    fun categoryDetailViewModel_updateCategory_isChanged() = runTest {
        // updateCategoryでCategoryのnameが更新されることとダイアログが閉じられることを確認
        val newCategory = colorRepository.getCategory().first().copy(name = "updated")
        viewModel.updateCategory(newCategory)
        assertThat(colorRepository.getCategory().first().name).isEqualTo(newCategory.name)
        coVerify { viewModel.closeNewCategoryDialog() }
    }

    @Test
    fun categoryDetailViewModel_deleteCategory_isDeleted() = runTest {
        // deleteCategoryでCategoryが削除されることとダイアログが閉じられること確認
        val deleteTarget = colorRepository.getCategory().first()
        assertThat(deleteTarget).isEqualTo(viewModel.uiState.value.category)
        viewModel.deleteCategory()
        assertThat(colorRepository.getCategory().first()).isNotEqualTo(deleteTarget)
        coVerify { viewModel.closeNewCategoryDialog() }
    }

    @Test
    fun categoryDetailViewModel_openNewCategoryDialog_isOpened() {
        // openNewCategoryDialogでダイアログが表示されることを確認
        assertThat(viewModel.uiState.value.isShowNewCategoryDialog).isFalse()
        viewModel.openNewCategoryDialog()
        assertThat(viewModel.uiState.value.isShowNewCategoryDialog).isTrue()
    }

    @Test
    fun categoryDetailViewModel_closeNewCategoryDialog_isOpened() {
        // openNewCategoryDialogでダイアログが閉じられることを確認
        viewModel.openNewCategoryDialog()
        assertThat(viewModel.uiState.value.isShowNewCategoryDialog).isTrue()
        viewModel.closeNewCategoryDialog()
        assertThat(viewModel.uiState.value.isShowNewCategoryDialog).isFalse()
    }
}