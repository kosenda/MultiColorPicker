package kosenda.makecolor.feature.makecolor.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.model.data.ColorTypeWithHex
import kosenda.makecolor.core.ui.feature.common.button.FloatingAddButton
import kosenda.makecolor.core.ui.feature.common.card.ColorValueTextsCard
import kosenda.makecolor.core.ui.feature.common.card.DisplayColorCard
import kosenda.makecolor.core.ui.feature.common.card.InputColorValueCard
import kosenda.makecolor.core.ui.feature.common.card.SpinnerCard
import kosenda.makecolor.core.ui.feature.common.topbar.TopBar
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.feature.makecolor.viewmodel.InputTextViewModel
import kosenda.makecolor.feature.makecolor.viewmodel.InputTextViewModelImpl
import kosenda.makecolor.feature.makecolor.viewmodel.PreviewInputTextViewModel
import kosenda.makecolor.feature.preview.PreviewSurface

@Composable
fun InputTextScreen(
    viewModel: InputTextViewModelImpl,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
    googleAd: @Composable () -> Unit,
) {
    InputTextScreenContent(
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
fun InputTextScreenContent(
    viewModel: InputTextViewModel,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
    googleAd: @Composable () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val colorTypeArray: Array<String> = arrayOf(
        ColorTypeWithHex.RGB.name,
        ColorTypeWithHex.CMYK.name,
        ColorTypeWithHex.HSV.name,
        ColorTypeWithHex.HEX.name,
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
                .padding(horizontal = 16.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(scrollState)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusManager.clearFocus()
                        },
                    )
                },
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

            InputColorValueCard(
                selectColorType = uiState.selectColorType,
                onRGBTextChange = viewModel::updateInputText,
                onCMYKTextChange = viewModel::updateInputText,
                onHSVTextChange = viewModel::updateInputText,
                onHexTextChange = viewModel::updateInputText,
                uiState = uiState,
            )

            ColorValueTextsCard(colorData = uiState.colorData)
            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewInputTextScreen_Light() {
    MakeColorTheme(isDarkTheme = false) {
        PreviewSurface {
            InputTextScreenContent(
                viewModel = PreviewInputTextViewModel(),
                onClickMenu = {},
                onClickInfo = {},
                onClickFloatingButton = {},
                onClickDisplayColor = {},
                googleAd = {},
            )
        }
    }
}

@Preview
@Composable
private fun PreviewInputTextScreen_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        PreviewSurface {
            InputTextScreenContent(
                viewModel = PreviewInputTextViewModel(),
                onClickMenu = {},
                onClickInfo = {},
                onClickFloatingButton = {},
                onClickDisplayColor = {},
                googleAd = {},
            )
        }
    }
}
