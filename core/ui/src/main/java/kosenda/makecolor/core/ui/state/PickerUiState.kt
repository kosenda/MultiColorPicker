package kosenda.makecolor.core.ui.state

import androidx.compose.ui.graphics.Color
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.model.data.HsvColorData
import kosenda.makecolor.core.ui.code.PickerType
import kosenda.makecolor.core.util.randomVividColorData

data class PickerUiState(
    val pickerType: PickerType = PickerType.CIRCLE,
    val harmonyMode: ColorHarmonyMode = ColorHarmonyMode.ANALOGOUS,
    val defaultColor: Color = Color.White,
    val colorData: ColorData = randomVividColorData(),
    val hsvColorData: HsvColorData = HsvColorData(),
)
