package kosenda.makecolor.feature.display.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.spyk
import kosenda.makecolor.core.data.default.copicColor
import kosenda.makecolor.core.data.default.jisIdiomaticColor
import kosenda.makecolor.core.data.default.jisSafetyColor
import kosenda.makecolor.core.data.default.webColor
import kosenda.makecolor.core.data.default.x11Color
import kosenda.makecolor.core.mock.FakeColorRepository
import kosenda.makecolor.core.mock.MockCategory
import kosenda.makecolor.core.mock.MockColorItem
import kosenda.makecolor.core.mock.MockDefaultCategories
import kosenda.makecolor.core.testing.MainDispatcherRule
import kosenda.makecolor.core.ui.data.ColorIndex
import kosenda.makecolor.core.ui.data.NavKey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SelectColorViewModelImplTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val colorRepository = spyk(FakeColorRepository())
    private val savedStateHandle = spyk(
        SavedStateHandle(
            mapOf(
                NavKey.CATEGORY.key to Json.encodeToString(MockCategory().item),
                NavKey.INDEX.key to ColorIndex.FIRST.num,
                NavKey.NEED_BACK.key to true,
            ),
        )
    )
    private fun makeViewModel() = SelectColorViewModelImpl(
        ioDispatcher = mainDispatcherRule.testDispatcher,
        colorRepository = colorRepository,
        savedStateHandle = savedStateHandle,
    )

    @Test
    fun selectColorViewModel_init_settingFromSavedStateHandle() {
        // initでSavedStateHandleに保存された値が取得されて設定されることを確認
        every {
            savedStateHandle.get<String>(NavKey.CATEGORY.key)!!
        } returns Json.encodeToString(MockCategory().item)
        every {
            savedStateHandle.get<Int>(NavKey.INDEX.key)
        } returns ColorIndex.SECOND.num
        every {
            savedStateHandle.get<Boolean>(NavKey.NEED_BACK.key)
        } returns false
        val viewModel = makeViewModel()
        assertThat(viewModel.uiState.value.category).isEqualTo(MockCategory().item)
        assertThat(viewModel.uiState.value.index).isEqualTo(ColorIndex.SECOND.num)
        assertThat(viewModel.uiState.value.needBack).isFalse()
    }

    @Test
    fun getColors_getRepository_isSettingUiState() = runTest {
        // repositoryに設定されている値を全て取得してUiStateに設定することを確認
        every {
            savedStateHandle.get<String>(NavKey.CATEGORY.key)!!
        } returns Json.encodeToString(MockCategory().item)
        coEvery {
            colorRepository.getColors(category = MockCategory().item.name)
        } returns MockColorItem().list
        val viewModel = makeViewModel()
        viewModel.getColors()
        assertThat(viewModel.uiState.value.colors).isEqualTo(MockColorItem().list)
        coVerify { colorRepository.getColors(category = MockCategory().item.name) }
    }

    @Test
    fun getColors_defaultCategories_isSettingUiState() = runTest {
        // defaultCategoriesに設定されている値を全て取得してUiStateに設定することを確認
        // X11Color
        every {
            savedStateHandle.get<String>(NavKey.CATEGORY.key)!!
        } returns Json.encodeToString(MockDefaultCategories().x11)
        var viewModel = makeViewModel()
        viewModel.getColors()
        assertThat(viewModel.uiState.value.colors).isEqualTo(x11Color)

        // WebColor
        every {
            savedStateHandle.get<String>(NavKey.CATEGORY.key)!!
        } returns Json.encodeToString(MockDefaultCategories().web)
        viewModel = makeViewModel()
        viewModel.getColors()
        assertThat(viewModel.uiState.value.colors).isEqualTo(webColor)

        // CopicColor
        every {
            savedStateHandle.get<String>(NavKey.CATEGORY.key)!!
        } returns Json.encodeToString(MockDefaultCategories().copic)
        viewModel = makeViewModel()
        viewModel.getColors()
        assertThat(viewModel.uiState.value.colors).isEqualTo(copicColor)

        // jisIdiomaticColor
        every {
            savedStateHandle.get<String>(NavKey.CATEGORY.key)!!
        } returns Json.encodeToString(MockDefaultCategories().jisIdiomatic)
        viewModel = makeViewModel()
        viewModel.getColors()
        assertThat(viewModel.uiState.value.colors).isEqualTo(jisIdiomaticColor)

        // jiSafetyColor
        every {
            savedStateHandle.get<String>(NavKey.CATEGORY.key)!!
        } returns Json.encodeToString(MockDefaultCategories().jisSafety)
        viewModel = makeViewModel()
        viewModel.getColors()
        assertThat(viewModel.uiState.value.colors).isEqualTo(jisSafetyColor)
    }

    @Test
    fun deleteColorItem_deleteRepository_isSettingUiState() = runTest {
        // repositoryから削除した値がUiStateからも削除されることを確認
        every {
            savedStateHandle.get<String>(NavKey.CATEGORY.key)!!
        } returns Json.encodeToString(MockCategory().item)
        coEvery {
            colorRepository.getColors(category = MockCategory().item.name)
        } returns MockColorItem().list
        val viewModel = makeViewModel()
        viewModel.getColors()
        assertThat(viewModel.uiState.value.colors.contains(MockColorItem().item)).isTrue()
        viewModel.deleteColorItem(MockColorItem().item)
        assertThat(viewModel.uiState.value.colors.contains(MockColorItem().item)).isFalse()
        coVerify {
            colorRepository.deleteColor(colorItem = MockColorItem().item)
        }
    }

}