package kosenda.makecolor.feature.display.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.ui.feature.common.extension.noRippleClickable
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.core.util.blackOrWhiteFromRGB
import kosenda.makecolor.core.util.hexToHexStrWithSharp
import kosenda.makecolor.core.util.randomColorData
import kosenda.makecolor.core.util.rgbToColor

@Composable
fun FullColorScreen(
    colorData: ColorData,
    onClick: () -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    val textColor = blackOrWhiteFromRGB(colorData.rgb)

    DisposableEffect(Unit) {
        systemUiController.isSystemBarsVisible = false
        onDispose {
            systemUiController.isSystemBarsVisible = true
        }
    }

    Surface(
        color = rgbToColor(colorData.rgb),
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier.padding(top = 40.dp).padding(all = 16.dp),
        ) {
            Text(
                text = colorData.rgb.toString(),
                color = textColor,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 8.dp),
            )
            Text(
                text = colorData.cmyk.toString(),
                color = textColor,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 8.dp),
            )
            Text(
                text = colorData.hsv.toString(),
                color = textColor,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 8.dp),
            )
            Text(
                text = hexToHexStrWithSharp(colorData.hex),
                color = textColor,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewColorFullScreen() {
    MakeColorTheme(isDarkTheme = false) {
        FullColorScreen(colorData = randomColorData()) {}
    }
}
