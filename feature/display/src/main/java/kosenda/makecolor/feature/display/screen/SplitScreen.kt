package kosenda.makecolor.feature.display.screen

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kosenda.makecolor.core.data.default.defaultCategories
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.data.ColorIndex
import kosenda.makecolor.core.ui.data.SplitColorNum
import kosenda.makecolor.core.ui.data.SplitColorParam
import kosenda.makecolor.core.ui.feature.common.ContentDivider
import kosenda.makecolor.core.ui.feature.common.SelectColorParam
import kosenda.makecolor.core.ui.feature.common.button.TonalButton
import kosenda.makecolor.core.ui.feature.common.card.SpinnerAndColorCard
import kosenda.makecolor.core.ui.feature.common.card.SpinnerCard
import kosenda.makecolor.core.ui.feature.common.topbar.TopBar
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.feature.display.viewmodel.PreviewSplitViewModel
import kosenda.makecolor.feature.display.viewmodel.SplitViewModel
import kosenda.makecolor.feature.display.viewmodel.SplitViewModelImpl
import kosenda.makecolor.feature.preview.PreviewSurface

@Composable
fun SplitScreen(
    viewModel: SplitViewModelImpl,
    navController: NavController,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickSplitColor: (SplitColorParam) -> Unit,
    onClickSelectColor: (SelectColorParam) -> Unit,
    googleAd: @Composable () -> Unit = {},
) {
    SplitScreenContent(
        viewModel = viewModel,
        navController = navController,
        onClickMenu = onClickMenu,
        onClickInfo = onClickInfo,
        onClickSelectColor = onClickSelectColor,
        onClickSplitColor = onClickSplitColor,
        googleAd = googleAd,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitScreenContent(
    viewModel: SplitViewModel,
    navController: NavController,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickSelectColor: (SelectColorParam) -> Unit,
    onClickSplitColor: (SplitColorParam) -> Unit,
    googleAd: @Composable () -> Unit = {},
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.init(navBackStackEntry = navController.currentBackStackEntry)
        viewModel.fetchCategories(defaultCategories = defaultCategories(context = context))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                scrollBehavior = scrollBehavior,
                onClickMenu = onClickMenu,
                onClickInfo = onClickInfo,
            )
        },
        bottomBar = googleAd,
        containerColor = Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SpinnerCard(
                    selectedText = " - ${uiState.selectSplitColorNum.value} - ",
                    categoryName = stringResource(id = R.string.split_num),
                    onSelectedChange = viewModel::updateSelectSplitColorNum,
                    displayItemList = mutableListOf<String>().apply {
                        SplitColorNum.values().map { num -> this.add(" - ${num.value} - ") }
                    }.toTypedArray(),
                )
                Spacer(modifier = Modifier.weight(1f))
                TonalButton(
                    text = stringResource(id = R.string.output),
                    painter = painterResource(id = R.drawable.ic_baseline_dashboard_customize_24),
                    onClick = {
                        onClickSplitColor(
                            SplitColorParam(
                                splitColorNum = uiState.selectSplitColorNum,
                                hex1 = uiState.selectHex1,
                                hex2 = uiState.selectHex2,
                                hex3 = uiState.selectHex3,
                                hex4 = uiState.selectHex4,
                            )
                        )
                    },
                )
            }

            ContentDivider(modifier = Modifier.padding(top = 8.dp))

            ColorIndex.values().map { colorIndex ->
                SpinnerAndColorCard(
                    colorIndex = colorIndex,
                    uiState = uiState,
                    updateSelectCategory = viewModel::updateSelectCategory,
                    onClickSelectColor = onClickSelectColor,
                )
            }

            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewSplitScreenContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        PreviewSurface {
            SplitScreenContent(
                viewModel = PreviewSplitViewModel(),
                navController = rememberNavController(),
                onClickMenu = {},
                onClickInfo = {},
                onClickSelectColor = {},
                onClickSplitColor = {},
            ) {}
        }
    }
}

@Preview
@Composable
private fun PreviewSplitScreenContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        PreviewSurface {
            SplitScreenContent(
                viewModel = PreviewSplitViewModel(),
                navController = rememberNavController(),
                onClickMenu = {},
                onClickInfo = {},
                onClickSelectColor = {},
                onClickSplitColor = {},
            ) {}
        }
    }
}
