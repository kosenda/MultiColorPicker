package kosenda.makecolor.view.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kosenda.makecolor.R
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.util.blackOrWhiteFromRGB
import kosenda.makecolor.core.util.randomColorData
import kosenda.makecolor.core.util.rgbToColor
import kosenda.makecolor.view.theme.MakeColorTheme

@Composable
fun DisplayColorCard(
    modifier: Modifier = Modifier,
    colorData: ColorData,
    height: Dp = 96.dp,
    isTappable: Boolean = true,
    onClickDisplayColor: (ColorData) -> Unit = {},
) {
    Card(
        modifier = modifier.height(height),
        colors = CardDefaults.cardColors(containerColor = rgbToColor(colorData.rgb)),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.tertiaryContainer),
    ) {
        when {
            isTappable -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            onClick = { onClickDisplayColor(colorData) },
                        ),
                    contentAlignment = Alignment.TopEnd,
                ) {
                    Text(
                        text = stringResource(id = R.string.tap_all_screen),
                        color = blackOrWhiteFromRGB(colorData.rgb),
                        modifier = Modifier.padding(top = 8.dp, end = 24.dp),
                    )
                }
            }
            else -> {
                Box(
                    modifier = Modifier
                        .height(height = height)
                        .fillMaxWidth()
                        .padding(all = 8.dp)
                        .background(
                            color = rgbToColor(colorData.rgb),
                            shape = RoundedCornerShape(16.dp),
                        ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDisplay() {
    MakeColorTheme(isDarkTheme = false) {
        DisplayColorCard(colorData = randomColorData()) {}
    }
}
