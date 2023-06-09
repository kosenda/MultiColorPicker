package kosenda.makecolor.feature.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.data.LocalIsExpandScreenClass
import kosenda.makecolor.core.ui.feature.common.button.CustomIconButton
import kosenda.makecolor.core.ui.feature.common.card.LanguageCard
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.core.ui.feature.theme.backgroundBrush

@Composable
fun SelectLanguageDialogContent(
    onClickClose: () -> Unit,
) {
    val isExpandScreenClass = LocalIsExpandScreenClass.current
    val languagePair = listOf(
        stringResource(id = R.string.locale_en) to stringResource(id = R.string.display_en),
        stringResource(id = R.string.locale_de) to stringResource(id = R.string.display_de),
        stringResource(id = R.string.locale_es) to stringResource(id = R.string.display_es),
        stringResource(id = R.string.locale_fr) to stringResource(id = R.string.display_fr),
        stringResource(id = R.string.locale_hi) to stringResource(id = R.string.display_hi),
        stringResource(id = R.string.locale_in) to stringResource(id = R.string.display_in),
        stringResource(id = R.string.locale_it) to stringResource(id = R.string.display_it),
        stringResource(id = R.string.locale_ja) to stringResource(id = R.string.display_ja),
        stringResource(id = R.string.locale_pt) to stringResource(id = R.string.display_pt),
        stringResource(id = R.string.locale_tr) to stringResource(id = R.string.display_tr),
        stringResource(id = R.string.locale_vi) to stringResource(id = R.string.display_vi),
        stringResource(id = R.string.locale_zh_tw) to stringResource(id = R.string.display_zh_tw),
        stringResource(id = R.string.locale_zh) to stringResource(id = R.string.display_zh),
    )
    var settingLocale by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val locale = AppCompatDelegate.getApplicationLocales()[0]
        locale?.let { settingLocale = locale.toLanguageTag() }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(brush = backgroundBrush()),
        containerColor = Color.Transparent,
    ) {
        Column(
            modifier = Modifier.padding(it),
        ) {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                CustomIconButton(
                    modifier = Modifier.padding(all = 8.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_close_24),
                    contentDescription = "close dialog",
                    onClick = onClickClose,
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = if (isExpandScreenClass) 3 else 2),
                contentPadding = PaddingValues(all = 4.dp)
            ) {
                items(items = languagePair) { (locale, displayLanguage) ->
                    LanguageCard(
                        displayLanguage = displayLanguage,
                        isSelected = settingLocale == locale,
                        onClick = {
                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(locale),
                            )
                        },
                    )
                }
                repeat(
                    times = if (languagePair.count() % 3 == 0) 1 else 4 - languagePair.count() % 3,
                ) {
                    item { Spacer(modifier = Modifier.height(72.dp)) }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSelectLanguageDialogContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        SelectLanguageDialogContent {}
    }
}

@Preview
@Composable
private fun PreviewSelectLanguageDialogContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        SelectLanguageDialogContent {}
    }
}
