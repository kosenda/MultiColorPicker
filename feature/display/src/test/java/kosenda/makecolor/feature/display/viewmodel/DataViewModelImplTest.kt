package kosenda.makecolor.feature.display.viewmodel

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kosenda.makecolor.core.domain.FetchCategoriesUseCase
import kosenda.makecolor.core.mock.MockCategory
import kosenda.makecolor.core.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DataViewModelImplTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fetchCategoriesUseCase: FetchCategoriesUseCase = mockk(relaxed = true)
    private val viewModel = DataViewModelImpl(
        ioDispatcher = mainDispatcherRule.testDispatcher,
        fetchCategoriesUseCase = fetchCategoriesUseCase,
    )

    @Test
    fun fetchCategories_fetchCategoriesUseCase_isCalledAndSetUiState() {
        // デフォルトのカテゴリーが取得されることを確認
        coEvery { fetchCategoriesUseCase(any()) } returns MockCategory().list
        assertThat(viewModel.uiState.value.categories).isEmpty()
        viewModel.fetchCategories(defaultCategories = listOf())
        assertThat(viewModel.uiState.value.categories).isNotEmpty()
        coVerify { fetchCategoriesUseCase(any()) }
    }
}