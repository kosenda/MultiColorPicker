package kosenda.makecolor.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kosenda.makecolor.core.ui.code.SplitColorNum
import kosenda.makecolor.core.util.hexToColor
import kosenda.makecolor.core.util.randomHex
import kosenda.makecolor.view.PreviewSurface
import kosenda.makecolor.view.navigation.SplitColorParam
import kosenda.makecolor.view.theme.MakeColorTheme

@Composable
fun SplitColorScreen(
    splitColorParam: SplitColorParam,
    onBackScreen: () -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    DisposableEffect(Unit) {
        systemUiController.isSystemBarsVisible = false
        onDispose {
            systemUiController.isSystemBarsVisible = true
        }
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = onBackScreen,
                ),
        ) {
            when (splitColorParam.splitColorNum) {
                SplitColorNum.TWO -> {
                    Column {
                        listOf(
                            splitColorParam.hex1,
                            splitColorParam.hex2,
                        ).map { hex ->
                            ColorBox(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                hex = hex,
                            )
                        }
                    }
                }
                SplitColorNum.THREE -> {
                    Column {
                        listOf(
                            splitColorParam.hex1,
                            splitColorParam.hex2,
                            splitColorParam.hex3,
                        ).map { hex ->
                            ColorBox(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                hex = hex,
                            )
                        }
                    }
                }
                SplitColorNum.FOUR -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(modifier = Modifier.weight(1f)) {
                            ColorBox(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f),
                                hex = splitColorParam.hex1,
                            )
                            ColorBox(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f),
                                hex = splitColorParam.hex2,
                            )
                        }
                        Row(modifier = Modifier.weight(1f)) {
                            ColorBox(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f),
                                hex = splitColorParam.hex3,
                            )
                            ColorBox(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f),
                                hex = splitColorParam.hex4,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorBox(
    modifier: Modifier = Modifier,
    hex: String,
) {
    Box(modifier = modifier.background(color = hexToColor(hex)))
}

@Preview
@Composable
private fun PreviewSplitColorScreen() {
    MakeColorTheme(isDarkTheme = false) {
        PreviewSurface {
            SplitColorScreen(
                splitColorParam = SplitColorParam(
                    splitColorNum = SplitColorNum.FOUR,
                    hex1 = randomHex(),
                    hex2 = randomHex(),
                    hex3 = randomHex(),
                    hex4 = randomHex(),
                ),
            ) {}
        }
    }
}
