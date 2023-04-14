package kosenda.makecolor.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.model.data.FontType
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.feature.common.ContentDivider
import kosenda.makecolor.core.ui.feature.common.button.CustomRadioButton
import kosenda.makecolor.core.ui.feature.common.card.TitleCard
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme

@Composable
fun SettingFontContent(
    selectFontType: FontType,
    onClickFontType: (FontType) -> Unit,
) {
    TitleCard(
        text = stringResource(id = R.string.font_setting),
        painter = painterResource(id = R.drawable.baseline_text_fields_24),
    )
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier.padding(vertical = 8.dp),
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            FontType.values().mapIndexed { index, fontType ->
                CustomRadioButton(
                    isSelected = fontType == selectFontType,
                    buttonText = fontType.fontName,
                    onClick = { onClickFontType(fontType) },
                )
                if (index < FontType.values().size - 1) {
                    ContentDivider()
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSettingFontContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        Column {
            SettingFontContent(selectFontType = FontType.DEFAULT) {}
        }
    }
}

@Preview
@Composable
private fun PreviewSettingFontContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        Column {
            SettingFontContent(selectFontType = FontType.DEFAULT) {}
        }
    }
}
