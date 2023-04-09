package kosenda.makecolor.view.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.R
import kosenda.makecolor.core.model.data.Theme
import kosenda.makecolor.core.ui.feature.common.ContentDivider
import kosenda.makecolor.core.ui.feature.common.card.TitleCard
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme

@Composable
fun SelectThemeContent(
    updateThemeNum: (Int) -> Unit,
    isSelectedThemeNum: (Int) -> Boolean,
) {
    val themeResources = listOf(
        Triple(
            Theme.NIGHT,
            stringResource(id = R.string.dark_mode),
            painterResource(id = R.drawable.ic_baseline_brightness_2_24),
        ),
        Triple(
            Theme.DAY,
            stringResource(id = R.string.light_mode),
            painterResource(id = R.drawable.ic_baseline_brightness_low_24),
        ),
        Triple(
            Theme.AUTO,
            stringResource(id = R.string.terminal_setting),
            painterResource(id = R.drawable.ic_baseline_brightness_auto_24),
        ),
    )

    TitleCard(
        text = stringResource(id = R.string.mode_switching),
        painter = painterResource(id = R.drawable.ic_baseline_brightness_2_24),
    )
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
        ) {
            themeResources.mapIndexed { index, (theme, text, painter) ->
                Row(
                    modifier = Modifier
                        .clickable { updateThemeNum(theme.num) }
                        .padding(start = 24.dp, end = 8.dp)
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painter,
                        contentDescription = text,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(24.dp),
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 12.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    RadioButton(
                        selected = isSelectedThemeNum(theme.num),
                        colors = RadioButtonDefaults.colors(),
                        onClick = { updateThemeNum(theme.num) },
                    )
                }

                if (index < themeResources.size - 1) {
                    ContentDivider()
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSelectThemeContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        Column {
            SelectThemeContent(updateThemeNum = {}, isSelectedThemeNum = { false })
        }
    }
}

@Preview
@Composable
private fun PreviewSelectThemeContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        Column {
            SelectThemeContent(updateThemeNum = {}, isSelectedThemeNum = { false })
        }
    }
}
