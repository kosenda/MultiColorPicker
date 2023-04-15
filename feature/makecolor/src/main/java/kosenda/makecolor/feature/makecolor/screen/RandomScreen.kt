package kosenda.makecolor.feature.makecolor.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.feature.common.button.FloatingAddButton
import kosenda.makecolor.core.ui.feature.common.button.TonalButton
import kosenda.makecolor.core.ui.feature.common.card.ColorValueTextsCard
import kosenda.makecolor.core.ui.feature.common.card.DisplayColorCard
import kosenda.makecolor.core.ui.feature.common.card.RandomColorsCard
import kosenda.makecolor.core.ui.feature.common.card.SpinnerCard
import kosenda.makecolor.core.ui.feature.common.topbar.TopBar
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.feature.makecolor.viewmodel.PreviewRandomViewModel
import kosenda.makecolor.feature.makecolor.viewmodel.RandomViewModel
import kosenda.makecolor.feature.makecolor.viewmodel.RandomViewModelImpl
import kosenda.makecolor.feature.preview.PreviewSurface

@Composable
fun RandomScreen(
    viewModel: RandomViewModelImpl,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
    googleAd: @Composable () -> Unit = {},
) {
    RandomScreenContent(
        viewModel = viewModel,
        onClickMenu = onClickMenu,
        onClickInfo = onClickInfo,
        onClickFloatingButton = onClickFloatingButton,
        onClickDisplayColor = onClickDisplayColor,
        googleAd = googleAd,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomScreenContent(
    viewModel: RandomViewModel,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
    googleAd: @Composable () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = androidx.compose.foundation.rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val horizontalPadding = 16.dp
    val randomTypeArray = stringArrayResource(id = R.array.random_category)
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp - horizontalPadding * 2
    val size = (screenWidth - horizontalPadding * 2 + 8.dp) / 5 - 8.dp

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
        bottomBar = googleAd,
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
                .padding(it)
                .padding(horizontal = horizontalPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(scrollState),
        ) {
            DisplayColorCard(
                colorData = uiState.colorData,
                onClickDisplayColor = onClickDisplayColor,
            )
            Column {
                SpinnerCard(
                    modifier = Modifier.padding(top = 8.dp),
                    selectedText = randomTypeArray[uiState.selectRandomType.index],
                    categoryName = stringResource(id = R.string.category),
                    onSelectedChange = viewModel::updateRandomType,
                    displayItemList = randomTypeArray,
                )
                RandomColorsCard(
                    randomRGBColors = uiState.randomRGBColors,
                    size = size,
                    updateColorData = viewModel::updateColorData,
                )
                TonalButton(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .fillMaxSize(),
                    text = stringResource(id = R.string.output_random),
                    onClick = viewModel::outputRandomColors,
                )
            }

            ColorValueTextsCard(colorData = uiState.colorData)
            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewRandomScreenContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        PreviewSurface {
            RandomScreenContent(
                viewModel = PreviewRandomViewModel(),
                onClickMenu = {},
                onClickInfo = {},
                onClickFloatingButton = {},
                onClickDisplayColor = {},
            ) {}
        }
    }
}

@Preview
@Composable
private fun PreviewRandomScreenContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        PreviewSurface {
            RandomScreenContent(
                viewModel = PreviewRandomViewModel(),
                onClickMenu = {},
                onClickInfo = {},
                onClickFloatingButton = {},
                onClickDisplayColor = {},
            ) {}
        }
    }
}
