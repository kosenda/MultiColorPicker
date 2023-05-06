package kosenda.makecolor.feature.display.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kosenda.makecolor.core.domain.FetchCategoriesUseCase
import kosenda.makecolor.core.mock.FakeColorRepository
import kosenda.makecolor.core.mock.MockCategory
import kosenda.makecolor.core.testing.MainDispatcherRule
import kosenda.makecolor.core.ui.data.ColorIndex
import kosenda.makecolor.core.ui.data.SplitColorNum
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SplitViewModelImplTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val colorRepository = spyk(FakeColorRepository())
    private val fetchCategoriesUseCase = spyk(
        FetchCategoriesUseCase(colorRepository)
    )
    private val viewModel = SplitViewModelImpl(
        ioDispatcher = mainDispatcherRule.testDispatcher,
        fetchCategoriesUseCase = fetchCategoriesUseCase,
    )

    @Test
    fun init_fourthIndexAndHex_settingUiState() {
        // initでSavedStateHandleに保存された値が取得されて設定されることを確認
        val navBackStackEntry = mockk<NavBackStackEntry>(relaxed = true)
        every {
            navBackStackEntry.savedStateHandle
        } returns SavedStateHandle(
            mapOf(
                "newHex" to testHex,
                "index" to ColorIndex.FOURTH.num,
            ),
        )
        viewModel.init(navBackStackEntry = navBackStackEntry)
        assertThat(viewModel.uiState.value.selectHex4).isEqualTo(testHex)
    }

    @Test
    fun fetchCategories_fetchCategoriesUseCase_isCalledAndSetUiState() = runTest {
        // デフォルトのカテゴリーが取得されることを確認
        assertThat(viewModel.uiState.value.categories).isEmpty()
        coEvery { fetchCategoriesUseCase(any()) } returns MockCategory().list
        viewModel.fetchCategories(defaultCategories = MockCategory().list)
        assertThat(viewModel.uiState.value.categories).isEqualTo(MockCategory().list)
        assertThat(viewModel.uiState.value.selectCategory1).isEqualTo(MockCategory().list.first())
        assertThat(viewModel.uiState.value.selectCategory2).isEqualTo(MockCategory().list.first())
        assertThat(viewModel.uiState.value.selectCategory3).isEqualTo(MockCategory().list.first())
        assertThat(viewModel.uiState.value.selectCategory4).isEqualTo(MockCategory().list.first())
        coVerify { fetchCategoriesUseCase(any()) }
    }

    @Test
    fun updateSelectSplitColorNum_index2_settingUiState() {
        // index2が更新されることを確認
        viewModel.updateSelectSplitColorNum(index = SplitColorNum.TWO.index)
        assertThat(viewModel.uiState.value.selectSplitColorNum).isEqualTo(SplitColorNum.TWO)
    }

    @Test
    fun updateSelectSplitColorNum_undefinedIndex_throwIllegalArgumentException() {
        // 未定義のindexが渡された場合、IllegalArgumentExceptionがthrowされることを確認
        Assert.assertThrows(IllegalArgumentException::class.java) {
            viewModel.updateSelectSplitColorNum(10)
        }
    }

    @Test
    fun updateSelectCategory_everyIndex_settingUiState() = runTest {
        // index1~4が更新されることを確認
        viewModel.fetchCategories(defaultCategories = MockCategory().list)
        val firstItem = MockCategory().list.first()
        viewModel.updateSelectCategory(categoryIndex = 0, colorIndex = ColorIndex.FIRST)
        assertThat(viewModel.uiState.value.selectCategory1).isEqualTo(firstItem)
        viewModel.updateSelectCategory(categoryIndex = 0, colorIndex = ColorIndex.SECOND)
        assertThat(viewModel.uiState.value.selectCategory2).isEqualTo(firstItem)
        viewModel.updateSelectCategory(categoryIndex = 0, colorIndex = ColorIndex.THIRD)
        assertThat(viewModel.uiState.value.selectCategory3).isEqualTo(firstItem)
        viewModel.updateSelectCategory(categoryIndex = 0, colorIndex = ColorIndex.FOURTH)
        assertThat(viewModel.uiState.value.selectCategory4).isEqualTo(firstItem)
    }

    companion object {
        const val testHex = "000000"
    }
}