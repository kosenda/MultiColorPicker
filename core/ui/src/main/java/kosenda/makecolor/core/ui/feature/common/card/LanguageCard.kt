package kosenda.makecolor.core.ui.feature.common.card

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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.feature.common.rememberButtonScaleState
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme

@Composable
fun LanguageCard(
    modifier: Modifier = Modifier,
    displayLanguage: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val buttonScaleState = rememberButtonScaleState()
    val cardHeight = LocalConfiguration.current.run {
        when {
            screenHeightDp > screenWidthDp -> (screenWidthDp / 4).dp
            else -> (screenHeightDp / 4).dp
        }
    }

    Card(
        modifier = modifier
            .padding(all = 4.dp)
            .height(cardHeight)
            .scale(scale = buttonScaleState.animationScale.value)
            .clickable(
                interactionSource = buttonScaleState.interactionSource,
                indication = null,
                onClick = onClick,
            ),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isSelected -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.surface
            },
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(1f),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = displayLanguage,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall,
                color = when {
                    isSelected -> MaterialTheme.colorScheme.secondaryContainer
                    else -> MaterialTheme.colorScheme.onSecondaryContainer
                },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLanguageCard_Light() {
    MakeColorTheme(isDarkTheme = false) {
        LanguageCard(
            displayLanguage = stringResource(id = R.string.display_ja),
            isSelected = false,
        ) {}
    }
}

@Preview
@Composable
private fun PreviewLanguageCard_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        LanguageCard(
            displayLanguage = stringResource(id = R.string.display_ja),
            isSelected = false,
        ) {}
    }
}
