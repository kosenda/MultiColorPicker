package kosenda.makecolor.view.screen

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
import kosenda.makecolor.R
import kosenda.makecolor.model.ColorData
import kosenda.makecolor.view.PreviewSurface
import kosenda.makecolor.view.component.GoogleAd
import kosenda.makecolor.view.component.button.FloatingAddButton
import kosenda.makecolor.view.component.button.TonalButton
import kosenda.makecolor.view.component.card.ColorValueTextsCard
import kosenda.makecolor.view.component.card.DisplayColorCard
import kosenda.makecolor.view.component.card.RandomColorsCard
import kosenda.makecolor.view.component.card.SpinnerCard
import kosenda.makecolor.view.component.topbar.TopBar
import kosenda.makecolor.view.theme.MakeColorTheme
import kosenda.makecolor.viewmodel.PreviewRandomViewModel
import kosenda.makecolor.viewmodel.RandomViewModel
import kosenda.makecolor.viewmodel.RandomViewModelImpl

@Composable
fun RandomScreen(
    viewModel: RandomViewModelImpl,
    onClickMenu: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
) {
    RandomScreenContent(
        viewModel = viewModel,
        onClickMenu = onClickMenu,
        onClickFloatingButton = onClickFloatingButton,
        onClickDisplayColor = onClickDisplayColor,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomScreenContent(
    viewModel: RandomViewModel,
    onClickMenu: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
    googleAd: @Composable () -> Unit = { GoogleAd() },
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
                onClickFloatingButton = {},
                onClickDisplayColor = {},
            ) {}
        }
    }
}
