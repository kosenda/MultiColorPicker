package kosenda.makecolor.feature.display.viewmodel

import androidx.navigation.NavBackStackEntry
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kosenda.makecolor.core.domain.FetchCategoriesUseCase
import kosenda.makecolor.core.mock.MockCategory
import kosenda.makecolor.core.testing.MainDispatcherRule
import kosenda.makecolor.core.ui.data.ColorIndex
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertThrows
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GradationViewModelImplTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fetchCategoriesUseCase: FetchCategoriesUseCase = mockk(relaxed = true)
    private val viewModel = GradationViewModelImpl(
        ioDispatcher = mainDispatcherRule.testDispatcher,
        fetchCategoriesUseCase = fetchCategoriesUseCase,
    )

    @Test
    fun init_firstIndex_updateUiState1() {
        // 初期化時にNavBackStackEntryからUiStateが更新されることを確認
        val originSelectHex1 = viewModel.uiState.value.selectHex1
        val newHex = "Test"
        val navBackStackEntry = mockk<NavBackStackEntry>(relaxed = true)
        coEvery {
            navBackStackEntry.savedStateHandle.get<String>(key = "newHex")
        } returns newHex
        coEvery {
            navBackStackEntry.savedStateHandle.get<Int>(key = "index")
        } returns ColorIndex.FIRST.num
        viewModel.init(navBackStackEntry)
        assertThat(viewModel.uiState.value.selectHex1).isEqualTo(newHex)
        assertThat(newHex).isNotEqualTo(originSelectHex1)
    }

    @Test
    fun init_secondIndex_updateUiState2() {
        // 初期化時にNavBackStackEntryからUiStateが更新されることを確認
        val originSelectHex2 = viewModel.uiState.value.selectHex2
        val newHex = "Test"
        val navBackStackEntry = mockk<NavBackStackEntry>(relaxed = true)
        coEvery {
            navBackStackEntry.savedStateHandle.get<String>(key = "newHex")
        } returns newHex
        coEvery {
            navBackStackEntry.savedStateHandle.get<Int>(key = "index")
        } returns ColorIndex.SECOND.num
        viewModel.init(navBackStackEntry)
        assertThat(viewModel.uiState.value.selectHex2).isEqualTo(newHex)
        assertThat(newHex).isNotEqualTo(originSelectHex2)
    }

    @Test
    fun init_undefinedIndex_throwIllegalArgumentException() {
        // 初期化時にNavBackStackEntryからUiStateが更新されることを確認
        val navBackStackEntry = mockk<NavBackStackEntry>(relaxed = true)
        coEvery {
            navBackStackEntry.savedStateHandle.get<Int>(key = "index")
        } returns 0
        coEvery {
            navBackStackEntry.savedStateHandle.get<String>(key = "newHex")
        } returns "Test"
        assertThrows(IllegalArgumentException::class.java) {
            viewModel.init(navBackStackEntry)
        }
    }

    @Test
    fun fetchCategories_fetchCategoriesUseCase_isCalledAndSetUiState() {
        // デフォルトのカテゴリーが取得されることを確認
        coEvery { fetchCategoriesUseCase(any()) } returns MockCategory().list
        assertThat(viewModel.uiState.value.categories).isEmpty()
        val originSelectCategory1 = viewModel.uiState.value.selectCategory1
        val originSelectCategory2 = viewModel.uiState.value.selectCategory2
        assertThat(viewModel.uiState.value.displayCategories).isEmpty()
        viewModel.fetchCategories(defaultCategories = listOf())
        assertThat(viewModel.uiState.value.categories).isNotEmpty()
        assertThat(viewModel.uiState.value.selectCategory1).isNotEqualTo(originSelectCategory1)
        assertThat(viewModel.uiState.value.selectCategory2).isNotEqualTo(originSelectCategory2)
        assertThat(viewModel.uiState.value.displayCategories).isNotEmpty()
        coVerify { fetchCategoriesUseCase(any()) }
    }

    @Test
    fun updateSelectCategory1_index_updateUiState() {
        // カテゴリー1が更新されることを確認
        coEvery { fetchCategoriesUseCase(any()) } returns MockCategory().list
        viewModel.fetchCategories(defaultCategories = listOf())
        val originSelectCategory1 = viewModel.uiState.value.selectCategory1
        val index = 1
        viewModel.updateSelectCategory1(index)
        assertThat(viewModel.uiState.value.selectCategory1)
            .isNotEqualTo(originSelectCategory1)
        assertThat(viewModel.uiState.value.selectCategory1)
            .isEqualTo(viewModel.uiState.value.categories[index])
    }

    @Test
    fun updateSelectCategory2_index_updateUiState() {
        // カテゴリー2が更新されることを確認
        coEvery { fetchCategoriesUseCase(any()) } returns MockCategory().list
        viewModel.fetchCategories(defaultCategories = listOf())
        val originSelectCategory2 = viewModel.uiState.value.selectCategory2
        val index = 1
        viewModel.updateSelectCategory2(index)
        assertThat(viewModel.uiState.value.selectCategory2)
            .isNotEqualTo(originSelectCategory2)
        assertThat(viewModel.uiState.value.selectCategory2)
            .isEqualTo(viewModel.uiState.value.categories[index])
    }
}
