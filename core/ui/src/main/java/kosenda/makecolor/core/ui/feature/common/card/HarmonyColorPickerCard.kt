package kosenda.makecolor.core.ui.feature.common.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.HsvColor
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker
import kosenda.makecolor.core.model.data.HsvColorData
import kosenda.makecolor.core.ui.feature.common.ColorCircle
import kosenda.makecolor.core.ui.feature.theme.MakeColorMaterial2Theme
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.core.util.colorToRGB
import kosenda.makecolor.core.util.hexToColor
import kosenda.makecolor.core.util.hsvColorToHexStr
import kosenda.makecolor.core.util.hsvColorToHsv
import kosenda.makecolor.core.util.hsvToRGB
import kosenda.makecolor.core.util.rgbToColor
import kosenda.makecolor.core.util.rgbToHex

@Composable
fun HarmonyColorPickerCard(
    defaultColor: Color,
    hsvColorData: HsvColorData,
    colorHarmonyMode: ColorHarmonyMode,
    horizontalPadding: Dp,
    onClickColorCircle: (HsvColor) -> Unit,
    onColorChanged: (HsvColor) -> Unit,
) {
    val screenMaxSize = LocalConfiguration.current.screenWidthDp.dp - horizontalPadding * 2
    val circleSize = (screenMaxSize - 8.dp * 3) / 5 - 8.dp
    var mainColorHex by rememberSaveable {
        mutableStateOf(rgbToHex(colorToRGB(color = defaultColor)).toString())
    }
    val colorPickerSize = when {
        LocalConfiguration.current.screenWidthDp > LocalConfiguration.current.screenHeightDp -> {
            LocalConfiguration.current.screenHeightDp.dp * 0.9f
        }
        else -> {
            LocalConfiguration.current.screenWidthDp.dp
        }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
                .padding(top = 16.dp),
        ) {
            Row(modifier = Modifier.padding(end = 8.dp)) {
                listOf(
                    hsvColorData.hsv1,
                    hsvColorData.hsv2,
                    hsvColorData.hsv3,
                    hsvColorData.hsv4,
                    hsvColorData.hsv5,
                ).map { hsvColor ->
                    ColorCircle(
                        modifier = Modifier.weight(1f),
                        color = rgbToColor(
                            rgb = hsvToRGB(hsv = hsvColorToHsv(hsvColor = hsvColor)),
                        ),
                        size = circleSize,
                        onClick = { onClickColorCircle(hsvColor) },
                    )
                }
            }
            MakeColorMaterial2Theme {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    HarmonyColorPicker(
                        modifier = Modifier
                            .size(size = colorPickerSize)
                            .padding(all = 8.dp),
                        harmonyMode = colorHarmonyMode,
                        color = HsvColor.from(color = hexToColor(hex = mainColorHex)),
                        onColorChanged = {
                            onColorChanged(it)
                            mainColorHex = hsvColorToHexStr(hsvColor = it)
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHarmonyColorPickerCard_Light() {
    MakeColorTheme(isDarkTheme = false) {
        MakeColorMaterial2Theme {
            val horizontalPadding = 16.dp
            Row(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                HarmonyColorPickerCard(
                    defaultColor = Color.Red,
                    hsvColorData = HsvColorData(),
                    colorHarmonyMode = ColorHarmonyMode.ANALOGOUS,
                    horizontalPadding = horizontalPadding,
                    onClickColorCircle = {},
                    onColorChanged = {},
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewHarmonyColorPickerCard_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        MakeColorMaterial2Theme {
            val horizontalPadding = 16.dp
            Row(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                HarmonyColorPickerCard(
                    defaultColor = Color.Red,
                    hsvColorData = HsvColorData(),
                    colorHarmonyMode = ColorHarmonyMode.ANALOGOUS,
                    horizontalPadding = horizontalPadding,
                    onClickColorCircle = {},
                    onColorChanged = {},
                )
            }
        }
    }
}
