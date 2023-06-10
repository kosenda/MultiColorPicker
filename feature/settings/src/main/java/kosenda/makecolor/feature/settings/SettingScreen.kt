package kosenda.makecolor.feature.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.feature.common.button.CustomButton
import kosenda.makecolor.core.ui.feature.common.card.TitleCard
import kosenda.makecolor.core.ui.feature.common.dialog.ConfirmDialog
import kosenda.makecolor.core.ui.feature.common.topbar.TopBar
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.feature.preview.PreviewSurface
import kotlinx.coroutines.launch

@Composable
fun SettingScreen(
    viewModel: SettingsViewModelImpl,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
) {
    SettingScreenContent(
        viewModel = viewModel,
        onClickMenu = onClickMenu,
        onClickInfo = onClickInfo,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreenContent(
    viewModel: SettingsViewModel,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                scrollBehavior = scrollBehavior,
                onClickMenu = onClickMenu,
                onClickInfo = onClickInfo,
            )
        },
        containerColor = Color.Transparent,
    ) { padding ->
        if (uiState.isShowSelectLanguageDialog) {
            SelectLanguageDialog(onClickClose = viewModel::closeSelectLanguageDialog)
        }
        if (uiState.isShowConfirmDialog) {
            ConfirmDialog(
                text = stringResource(id = R.string.confirm_delete),
                onDismissRequest = viewModel::closeConfirmDialog,
                onClick = {
                    viewModel.closeConfirmDialog()
                    coroutineScope.launch {
                        viewModel.deleteAllData()
                        Toast.makeText(context, R.string.deleted, Toast.LENGTH_SHORT).show()
                    }
                },
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = 16.dp),
        ) {
            SelectThemeContent(
                updateThemeNum = viewModel::updateThemeNum,
                isSelectedThemeNum = viewModel::isSelectedThemeNum,
            )

            TitleCard(
                text = stringResource(id = R.string.language_settings),
                painter = painterResource(id = R.drawable.ic_baseline_language_24),
            )
            CustomButton(
                text = stringResource(id = R.string.select_language),
                onClick = viewModel::openSelectLanguageDialog,
            )

            SettingFontContent(
                selectFontType = viewModel.fontType.value,
                onClickFontType = { fontType -> viewModel.updateFontType(newFontType = fontType) },
            )

            TitleCard(
                text = stringResource(id = R.string.data_management),
                painter = painterResource(id = R.drawable.ic_baseline_delete_outline_24),
            )
            CustomButton(
                modifier = Modifier.padding(bottom = 24.dp),
                text = stringResource(id = R.string.all_delete),
                onClick = viewModel::openConfirmDialog,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSettingScreenContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        PreviewSurface {
            SettingScreenContent(
                viewModel = PreviewSettingsViewModel(),
                onClickMenu = {},
                onClickInfo = {},
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSettingScreenContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        PreviewSurface {
            SettingScreenContent(
                viewModel = PreviewSettingsViewModel(),
                onClickMenu = {},
                onClickInfo = {},
            )
        }
    }
}
