package kosenda.makecolor.feature.screen

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kosenda.makecolor.core.data.default.defaultCategories
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.data.ColorIndex
import kosenda.makecolor.core.ui.feature.common.ImagePicker
import kosenda.makecolor.core.ui.feature.common.SelectColorParam
import kosenda.makecolor.core.ui.feature.common.button.FloatingAddButton
import kosenda.makecolor.core.ui.feature.common.card.ColorValueTextsCard
import kosenda.makecolor.core.ui.feature.common.card.DisplayColorCard
import kosenda.makecolor.core.ui.feature.common.card.HexAndDisplayColorCard
import kosenda.makecolor.core.ui.feature.common.card.SpinnerCard
import kosenda.makecolor.core.ui.feature.common.topbar.TopBar
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.core.util.getNameIfNoAlias
import kosenda.makecolor.feature.preview.PreviewSurface
import kosenda.makecolor.feature.viewmodel.MergeViewModel
import kosenda.makecolor.feature.viewmodel.MergeViewModelImpl
import kosenda.makecolor.feature.viewmodel.PreviewMergeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MergeScreen(
    viewModel: MergeViewModelImpl,
    navController: NavController,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
    onClickSelectColor: (SelectColorParam) -> Unit,
) {
    MergeScreenContent(
        viewModel = viewModel,
        navController = navController,
        onClickMenu = onClickMenu,
        onClickInfo = onClickInfo,
        onClickFloatingButton = onClickFloatingButton,
        onClickDisplayColor = onClickDisplayColor,
        onClickSelectColor = onClickSelectColor,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MergeScreenContent(
    viewModel: MergeViewModel,
    navController: NavController,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
    onClickSelectColor: (SelectColorParam) -> Unit,
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scrollState = rememberScrollState()
    val horizontalPadding = 16.dp
    val uiState by viewModel.uiState.collectAsState()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp - horizontalPadding * 2
    val density = LocalDensity.current
    val btmHeight = (screenWidth + 1.dp).value * density.density
    val btmWidth = (250.dp + 1.dp).value * density.density
    val layoutDirection = LocalLayoutDirection.current

    LaunchedEffect(Unit) {
        viewModel.init(navBackStackEntry = navController.currentBackStackEntry)
        viewModel.fetchCategories(defaultCategories(context = context))
    }

    LaunchedEffect(uiState.selectHex1, uiState.selectHex2) {
        withContext(Dispatchers.Main) { viewModel.resetBitmap() }
        viewModel.createBitmap(
            hex1 = uiState.selectHex1,
            hex2 = uiState.selectHex2,
            btmHeight = btmHeight,
            btmWidth = btmWidth,
            density = density,
            layoutDirection = layoutDirection,
        )
    }

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
                modifier = Modifier.padding(bottom = 16.dp),
                colorData = uiState.colorData,
                onClickDisplayColor = onClickDisplayColor,
            )

            uiState.bitmap?.let { btm ->
                ImagePicker(
                    btm = btm,
                    heightToWidthRatio = 2f / 3f,
                    horizontalPadding = horizontalPadding,
                    updateColorData = viewModel::updateColorData,
                )
            }

            SpinnerCard(
                modifier = Modifier.padding(top = 8.dp),
                selectedText = uiState.selectCategory1.getNameIfNoAlias(),
                categoryName = stringResource(id = R.string.select_1),
                onSelectedChange = viewModel::updateSelectCategory1,
                displayItemList = uiState.displayCategory.toTypedArray(),
            )
            HexAndDisplayColorCard(
                hex = uiState.selectHex1,
                onClick = {
                    onClickSelectColor(
                        SelectColorParam(
                            category = uiState.selectCategory1,
                            index = ColorIndex.FIRST,
                        ),
                    )
                },
            )

            SpinnerCard(
                selectedText = uiState.selectCategory2.getNameIfNoAlias(),
                categoryName = stringResource(id = R.string.select_2),
                onSelectedChange = viewModel::updateSelectCategory2,
                displayItemList = uiState.displayCategory.toTypedArray(),
            )
            HexAndDisplayColorCard(
                hex = uiState.selectHex2,
                onClick = {
                    onClickSelectColor(
                        SelectColorParam(
                            category = uiState.selectCategory2,
                            index = ColorIndex.SECOND,
                        ),
                    )
                },
            )

            ColorValueTextsCard(colorData = uiState.colorData)
            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewMergeScreenContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        PreviewSurface {
            MergeScreenContent(
                viewModel = PreviewMergeViewModel(),
                navController = rememberNavController(),
                onClickMenu = {},
                onClickInfo = {},
                onClickFloatingButton = {},
                onClickDisplayColor = {},
                onClickSelectColor = {},
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMergeScreenContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        PreviewSurface {
            MergeScreenContent(
                viewModel = PreviewMergeViewModel(),
                navController = rememberNavController(),
                onClickMenu = {},
                onClickInfo = {},
                onClickFloatingButton = {},
                onClickDisplayColor = {},
                onClickSelectColor = {},
            )
        }
    }
}
