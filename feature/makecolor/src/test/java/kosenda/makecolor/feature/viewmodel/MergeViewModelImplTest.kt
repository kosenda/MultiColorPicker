package kosenda.makecolor.feature.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavBackStackEntry
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kosenda.makecolor.core.domain.FetchCategoriesUseCase
import kosenda.makecolor.core.domain.UpdateOtherColorUseCase
import kosenda.makecolor.core.mock.FakeColorRepository
import kosenda.makecolor.core.mock.MockCategory
import kosenda.makecolor.core.testing.MainDispatcherRule
import kosenda.makecolor.core.ui.data.ColorIndex
import kosenda.makecolor.core.util.colorToRGB
import kosenda.makecolor.core.util.convertDisplayStringListFromCategories
import kosenda.makecolor.core.util.rgbToColorData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MergeViewModelImplTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val fetchCategoriesUseCase: FetchCategoriesUseCase = spyk(
        FetchCategoriesUseCase(colorRepository = FakeColorRepository())
    )
    private val viewModel = MergeViewModelImpl(
        ioDispatcher = mainDispatcherRule.testDispatcher,
        updateOtherColorUseCase = UpdateOtherColorUseCase(),
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
        Assert.assertThrows(IllegalArgumentException::class.java) {
            viewModel.init(navBackStackEntry)
        }
    }

    @Test
    fun fetchCategories_fetchCategoriesUseCase_isCalledAndSetUiState() = runTest {
        // デフォルトのカテゴリーが取得されてUiStateが更新されることを確認
        coEvery { fetchCategoriesUseCase(any()) } returns MockCategory().list
        assertThat(viewModel.uiState.value.categories).isEmpty()
        viewModel.fetchCategories(defaultCategories = listOf())
        assertThat(viewModel.uiState.value.categories).isNotEmpty()
        assertThat(viewModel.uiState.value.selectCategory1).isEqualTo(MockCategory().list.first())
        assertThat(viewModel.uiState.value.selectCategory2).isEqualTo(MockCategory().list.first())
        assertThat(viewModel.uiState.value.displayCategory)
            .isEqualTo(convertDisplayStringListFromCategories(MockCategory().list))
        coVerify { fetchCategoriesUseCase(any()) }
    }

    @Test
    fun updateColorData_newColor_isChanged() {
        // 色の更新ができることを確認
        val isRed = viewModel.uiState.value.colorData == rgbToColorData(colorToRGB(Color.Red))
        val newColor = if (isRed) Color.Blue else Color.Red
        assertThat(viewModel.uiState.value.colorData)
            .isNotEqualTo(rgbToColorData(colorToRGB(newColor)))
        viewModel.updateColorData(color = newColor)
        assertThat(viewModel.uiState.value.colorData)
            .isEqualTo(rgbToColorData(colorToRGB(newColor)))
    }

    @Ignore("実機でするテスト")
    @Test
    fun createBitmap_value_isNotNull() {
        // Bitmapの生成ができることを確認
        assertThat(viewModel.uiState.value.bitmap).isNull()
        viewModel.createBitmap(
            hex1 = "FFFFFF",
            hex2 = "000000",
            btmHeight = 100f,
            btmWidth = 100f,
            density = Density(context),
            layoutDirection = LayoutDirection.Rtl,
        )
        assertThat(viewModel.uiState.value.bitmap).isNotNull()
    }

    @Ignore("実機でするテスト")
    @Test
    fun resetBitmap_once_isNull() {
        // Bitmapのリセットができることを確認
        every { viewModel.createBitmap(any(), any(), any(), any(), any(), any()) } returns mockk("Bitmap")
        assertThat(viewModel.uiState.value.bitmap).isNotNull()
        viewModel.resetBitmap()
        assertThat(viewModel.uiState.value.bitmap).isNull()
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