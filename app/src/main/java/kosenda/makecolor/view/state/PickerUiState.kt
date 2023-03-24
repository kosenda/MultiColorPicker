package kosenda.makecolor.view.state

import androidx.compose.ui.graphics.Color
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import kosenda.makecolor.model.ColorData
import kosenda.makecolor.model.HsvColorData
import kosenda.makecolor.model.util.randomVividColorData
import kosenda.makecolor.view.code.PickerType

data class PickerUiState(
    val pickerType: PickerType = PickerType.CIRCLE,
    val harmonyMode: ColorHarmonyMode = ColorHarmonyMode.ANALOGOUS,
    val defaultColor: Color = Color.White,
    val colorData: ColorData = randomVividColorData(),
    val hsvColorData: HsvColorData = HsvColorData(),
)
