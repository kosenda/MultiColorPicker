package kosenda.makecolor.view.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kosenda.makecolor.R
import kosenda.makecolor.model.util.blackOrWhiteFromRGB
import kosenda.makecolor.model.util.hexToColor
import kosenda.makecolor.model.util.hexToRGB
import kosenda.makecolor.model.util.randomHex
import kosenda.makecolor.view.theme.MakeColorTheme

@Composable
fun DisplayGradationColorCard(
    leftHex: String,
    rightHex: String,
    height: Dp = 96.dp,
    onClickDisplayGradationColor: (String, String) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(all = 8.dp)
            .height(height),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.tertiaryContainer),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            hexToColor(leftHex),
                            hexToColor(rightHex),
                        ),
                    ),
                )
                .clickable(
                    onClick = { onClickDisplayGradationColor(leftHex, rightHex) },
                ),
            contentAlignment = Alignment.TopEnd,
        ) {
            Text(
                text = stringResource(id = R.string.tap_all_screen),
                color = blackOrWhiteFromRGB(hexToRGB(rightHex)),
                modifier = Modifier.padding(top = 8.dp, end = 24.dp),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDisplayGradationColorCard() {
    MakeColorTheme(isDarkTheme = false) {
        DisplayGradationColorCard(
            leftHex = randomHex(),
            rightHex = randomHex(),
            onClickDisplayGradationColor = { _, _ -> },
        )
    }
}
