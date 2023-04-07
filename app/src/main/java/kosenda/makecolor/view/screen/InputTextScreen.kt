package kosenda.makecolor.view.screen

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
import kosenda.makecolor.R
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.model.ColorTypeWithHex
import kosenda.makecolor.view.PreviewSurface
import kosenda.makecolor.view.component.GoogleAd
import kosenda.makecolor.view.component.button.FloatingAddButton
import kosenda.makecolor.view.component.card.ColorValueTextsCard
import kosenda.makecolor.view.component.card.DisplayColorCard
import kosenda.makecolor.view.component.card.InputColorValueCard
import kosenda.makecolor.view.component.card.SpinnerCard
import kosenda.makecolor.view.component.topbar.TopBar
import kosenda.makecolor.view.theme.MakeColorTheme
import kosenda.makecolor.viewmodel.InputTextViewModel
import kosenda.makecolor.viewmodel.InputTextViewModelImpl
import kosenda.makecolor.viewmodel.PreviewInputTextViewModel

@Composable
fun InputTextScreen(
    viewModel: InputTextViewModelImpl,
    onClickMenu: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
) {
    InputTextScreenContent(
        viewModel = viewModel,
        onClickMenu = onClickMenu,
        onClickFloatingButton = onClickFloatingButton,
        onClickDisplayColor = onClickDisplayColor,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextScreenContent(
    viewModel: InputTextViewModel,
    onClickMenu: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
    googleAd: @Composable () -> Unit = { GoogleAd() },
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
                onClickFloatingButton = {},
                onClickDisplayColor = {},
                googleAd = {},
            )
        }
    }
}
