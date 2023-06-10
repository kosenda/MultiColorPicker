package kosenda.makecolor.feature.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.model.data.ColorType
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.feature.common.button.FloatingAddButton
import kosenda.makecolor.core.ui.feature.common.card.ColorValueTextsCard
import kosenda.makecolor.core.ui.feature.common.card.DisplayColorCard
import kosenda.makecolor.core.ui.feature.common.card.SeekbarsCard
import kosenda.makecolor.core.ui.feature.common.card.SpinnerCard
import kosenda.makecolor.core.ui.feature.common.topbar.TopBar
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.feature.preview.PreviewSurface
import kosenda.makecolor.feature.viewmodel.PreviewSeekbarViewModel
import kosenda.makecolor.feature.viewmodel.SeekbarViewModel
import kosenda.makecolor.feature.viewmodel.SeekbarViewModelImpl

@Composable
fun SeekbarScreen(
    viewModel: SeekbarViewModelImpl,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
) {
    SeekbarScreenContent(
        viewModel = viewModel,
        onClickMenu = onClickMenu,
        onClickInfo = onClickInfo,
        onClickFloatingButton = onClickFloatingButton,
        onClickDisplayColor = onClickDisplayColor,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeekbarScreenContent(
    viewModel: SeekbarViewModel,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scrollState = rememberScrollState()
    val colorTypeArray: Array<String> = arrayOf(
        ColorType.RGB.name,
        ColorType.CMYK.name,
        ColorType.HSV.name,
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                scrollBehavior = scrollBehavior,
                onClickMenu = onClickMenu,
                onClickInfo = onClickInfo,
                hex1 = uiState.colorData.hex.toString(),
            )
        },
        floatingActionButton = {
            FloatingAddButton(
                onClick = { onClickFloatingButton(uiState.colorData) },
                scrollState = scrollState,
            )
        },
        containerColor = Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(scrollState)
                .padding(it)
                .padding(horizontal = 16.dp),
        ) {
            DisplayColorCard(
                colorData = uiState.colorData,
                onClickDisplayColor = onClickDisplayColor,
            )
            SpinnerCard(
                modifier = Modifier.padding(top = 8.dp),
                selectedText = uiState.selectColorType.name,
                categoryName = stringResource(id = R.string.category),
                onSelectedChange = viewModel::updateSelectColorType,
                displayItemList = colorTypeArray,
            )
            SeekbarsCard(
                selectColorType = uiState.selectColorType,
                onRGBColorChange = viewModel::updateColorData,
                onCMYKColorChange = viewModel::updateColorData,
                onHSVColorChange = viewModel::updateColorData,
                colorData = uiState.colorData,
            )
            ColorValueTextsCard(colorData = uiState.colorData)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(150.dp),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSeekbarScreenContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        PreviewSurface {
            SeekbarScreenContent(
                viewModel = PreviewSeekbarViewModel(),
                onClickMenu = {},
                onClickInfo = {},
                onClickFloatingButton = {},
                onClickDisplayColor = {},
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSeekbarScreenContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        PreviewSurface {
            SeekbarScreenContent(
                viewModel = PreviewSeekbarViewModel(),
                onClickMenu = {},
                onClickInfo = {},
                onClickFloatingButton = {},
                onClickDisplayColor = {},
            )
        }
    }
}
