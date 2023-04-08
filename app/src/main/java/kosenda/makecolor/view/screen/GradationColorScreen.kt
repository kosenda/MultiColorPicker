package kosenda.makecolor.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kosenda.makecolor.core.util.hexToColor
import kosenda.makecolor.core.util.randomHex
import kosenda.makecolor.view.theme.MakeColorTheme

@Composable
fun GradationColorScreen(
    hex1: String,
    hex2: String,
    onClick: () -> Unit,
) {
    val systemUiController = rememberSystemUiController()

    DisposableEffect(Unit) {
        systemUiController.isSystemBarsVisible = false
        onDispose {
            systemUiController.isSystemBarsVisible = true
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = onClick,
                )
                .background(
                    brush = Brush.verticalGradient(
                        listOf(hexToColor(hex = hex1), hexToColor(hex = hex2)),
                    ),
                ),
        )
    }
}

@Preview
@Composable
private fun PreviewGradationColorScreen() {
    MakeColorTheme(isDarkTheme = false) {
        GradationColorScreen(hex1 = randomHex(), hex2 = randomHex()) {}
    }
}
