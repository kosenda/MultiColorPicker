package kosenda.makecolor.feature.display.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.mock.MockColorItem
import kosenda.makecolor.core.ui.data.NavKey
import kosenda.makecolor.core.util.hexToRGB
import kosenda.makecolor.core.util.rgbToColorData
import kosenda.makecolor.core.util.rgbToComplementaryRgb
import kosenda.makecolor.core.util.rgbToOppositeRgb
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test

class ColorDetailViewModelImplTest {

    private val savedStateHandle = SavedStateHandle(
        mapOf(
            NavKey.COLOR_ITEM.key to Json.encodeToString(MockColorItem().item),
            NavKey.CATEGORY_NAME.key to MockColorItem().item.category,
        ),
    )
    private val viewModel = ColorDetailViewModelImpl(
        savedStateHandle = savedStateHandle,
    )

    @Test
    fun colorDetailViewModel_init_settingSavedStateHandle() {
        // 初期化時にsavedStateHandelで取得した値を設定できることを確認
        val mockColorItemRGB = hexToRGB(MockColorItem().item.hex)
        assertThat(viewModel.uiState.value.memo).isEqualTo(MockColorItem().item.memo)
        assertThat(viewModel.uiState.value.categoryName).isEqualTo(MockColorItem().item.category)
        assertThat(viewModel.uiState.value.colorData)
            .isEqualTo(rgbToColorData(rgb = mockColorItemRGB))
        assertThat(viewModel.uiState.value.complementaryColorData)
            .isEqualTo(rgbToColorData(rgbToComplementaryRgb(rgb = mockColorItemRGB)))
        assertThat(viewModel.uiState.value.oppositeColorData)
            .isEqualTo(rgbToColorData(rgbToOppositeRgb(rgb = mockColorItemRGB)))
    }
}