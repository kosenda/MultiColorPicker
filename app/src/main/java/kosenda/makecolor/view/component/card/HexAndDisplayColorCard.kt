package kosenda.makecolor.view.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.R
import kosenda.makecolor.core.util.hexToColor
import kosenda.makecolor.core.util.randomHex
import kosenda.makecolor.view.theme.MakeColorTheme

@Composable
fun HexAndDisplayColorCard(
    hex: String,
    onClick: (() -> Unit)? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(56.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = when (onClick) {
                null -> Modifier
                else -> Modifier.clickable(onClick = onClick)
            },
        ) {
            if (hex != "") {
                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = hexToColor(hex)),
                    )
                }
            }
            Text(
                text = when {
                    hex != "" -> "#$hex"
                    else -> stringResource(id = R.string.not_select)
                },
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewHexAndDisplayColorCard_Light() {
    MakeColorTheme(isDarkTheme = false) {
        HexAndDisplayColorCard(hex = randomHex())
    }
}

@Preview
@Composable
private fun PreviewHexAndDisplayColorCard_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        HexAndDisplayColorCard(hex = randomHex())
    }
}
