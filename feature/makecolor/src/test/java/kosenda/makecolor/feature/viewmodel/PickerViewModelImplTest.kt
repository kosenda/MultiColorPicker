package kosenda.makecolor.feature.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import io.mockk.spyk
import io.mockk.verify
import kosenda.makecolor.core.domain.UpdateOtherColorUseCase
import kosenda.makecolor.core.domain.UpdatePaletteColorsUseCase
import kosenda.makecolor.core.testing.MainDispatcherRule
import kosenda.makecolor.core.util.colorToRGB
import kosenda.makecolor.core.util.rgbToColorData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class PickerViewModelImplTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val updatePaletteColorsUseCase = spyk(UpdatePaletteColorsUseCase())
    private val updateOtherColorUseCase = UpdateOtherColorUseCase()
    private val viewModel = PictureViewModelImpl(
        updateOtherColorUseCase = updateOtherColorUseCase,
        updatePaletteColorsUseCase = updatePaletteColorsUseCase,
        ioDispatcher = mainDispatcherRule.testDispatcher,
    )

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

    @Test
    fun makeBitmapAndPalette_uriAndContentResolver_isMadeBitMapAndPalette() {
        // uriとcontentResolverを渡すとbitmapとpaletteが作成されることを確認
        assertThat(viewModel.uiState.value.bitmap).isNull()
        viewModel.makeBitmapAndPalette(
            uri = Uri.parse("android.resource://kosenda.makecolor/drawable/test"),
            contentResolver = context.contentResolver,
        )
        assertThat(viewModel.uiState.value.bitmap).isNotNull()
        verify { updatePaletteColorsUseCase(any(), any()) }
    }

    @Test
    fun resetImage_once_isClear() {
        // 一度だけ画像をリセットするとBitMapがクリアされることを確認
        viewModel.makeBitmapAndPalette(
            uri = Uri.parse("android.resource://kosenda.makecolor/drawable/test"),
            contentResolver = context.contentResolver,
        )
        assertThat(viewModel.uiState.value.bitmap).isNotNull()
        viewModel.resetImage()
        assertThat(viewModel.uiState.value.bitmap).isNull()
        verify { updatePaletteColorsUseCase(any(), any()) }
    }
}