package kosenda.makecolor.feature.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.feature.common.ImagePicker
import kosenda.makecolor.core.ui.feature.common.button.FloatingAddButton
import kosenda.makecolor.core.ui.feature.common.button.TonalButton
import kosenda.makecolor.core.ui.feature.common.card.ColorValueTextsCard
import kosenda.makecolor.core.ui.feature.common.card.DisplayColorCard
import kosenda.makecolor.core.ui.feature.common.card.PaletteCircleCard
import kosenda.makecolor.core.ui.feature.common.card.SelectImageCard
import kosenda.makecolor.core.ui.feature.common.topbar.TopBar
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.feature.preview.PreviewSurface
import kosenda.makecolor.feature.viewmodel.PictureViewModel
import kosenda.makecolor.feature.viewmodel.PictureViewModelImpl
import kosenda.makecolor.feature.viewmodel.PreviewPictureViewModel

@Composable
fun PictureScreen(
    viewModel: PictureViewModelImpl,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
) {
    PictureScreenContent(
        viewModel = viewModel,
        onClickMenu = onClickMenu,
        onClickInfo = onClickInfo,
        onClickFloatingButton = onClickFloatingButton,
        onClickDisplayColor = onClickDisplayColor,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureScreenContent(
    viewModel: PictureViewModel,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val contentResolver = LocalContext.current.contentResolver
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val horizontalPadding = 16.dp
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let { uri ->
                viewModel.makeBitmapAndPalette(uri = uri, contentResolver = contentResolver)
            }
        },
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
                .padding(horizontal = horizontalPadding),
        ) {
            DisplayColorCard(
                colorData = uiState.colorData,
                onClickDisplayColor = onClickDisplayColor,
            )
            if (uiState.bitmap == null) {
                SelectImageCard(onClick = { launcher.launch("image/*") })
            } else {
                PaletteCircleCard(
                    paletteColors = uiState.paletteColors,
                    horizontalPadding = horizontalPadding,
                    updateColorData = viewModel::updateColorData,
                )
                uiState.bitmap?.let { btm ->
                    ImagePicker(
                        btm = btm,
                        horizontalPadding = horizontalPadding,
                        updateColorData = viewModel::updateColorData,
                    )
                    Row(
                        modifier = Modifier.padding(top = 16.dp),
                    ) {
                        TonalButton(
                            text = stringResource(id = R.string.reset),
                            painter = painterResource(id = R.drawable.baseline_cached_24),
                            onClick = viewModel::resetImage,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            ColorValueTextsCard(
                modifier = Modifier.padding(top = 8.dp),
                colorData = uiState.colorData,
            )
            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewPictureScreenContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        PreviewSurface {
            PictureScreenContent(
                viewModel = PreviewPictureViewModel(),
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
private fun PreviewPictureScreenContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        PreviewSurface {
            PictureScreenContent(
                viewModel = PreviewPictureViewModel(),
                onClickMenu = {},
                onClickInfo = {},
                onClickFloatingButton = {},
                onClickDisplayColor = {},
            )
        }
    }
}
