package kosenda.makecolor.feature.viewmodel

import androidx.compose.ui.graphics.Color
import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.domain.UpdateOtherColorUseCase
import kosenda.makecolor.core.model.data.RandomType
import kosenda.makecolor.core.util.colorToRGB
import kosenda.makecolor.core.util.rgbToColorData
import org.junit.Test

class RandomViewModelImplTest {

    private val viewModel = RandomViewModelImpl(
        updateOtherColorUseCase = UpdateOtherColorUseCase(),
    )

    @Test
    fun updateColorData_newColor_updatedUiState() {
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
    fun updateRandomType_everyIndex_updatedUiState() {
        // ランダムの種類の更新ができることを確認
        viewModel.updateRandomType(index = RandomType.PASTEL.index)
        assertThat(viewModel.uiState.value.selectRandomType).isEqualTo(RandomType.PASTEL)
        viewModel.updateRandomType(index = RandomType.BLACK_AND_WHITE.index)
        assertThat(viewModel.uiState.value.selectRandomType).isEqualTo(RandomType.BLACK_AND_WHITE)
        viewModel.updateRandomType(index = RandomType.VIVID.index)
        assertThat(viewModel.uiState.value.selectRandomType).isEqualTo(RandomType.VIVID)
        viewModel.updateRandomType(index = RandomType.NOT_SPECIFIED.index)
        assertThat(viewModel.uiState.value.selectRandomType).isEqualTo(RandomType.NOT_SPECIFIED)
    }

    @Test
    fun outputRandomColors_once_updatedUiState() {
        // ランダムの色の出力ができることを確認
        val originRandomRGBColors = viewModel.uiState.value.randomRGBColors
        viewModel.outputRandomColors()
        assertThat(viewModel.uiState.value.randomRGBColors).isNotEqualTo(originRandomRGBColors)
    }
}