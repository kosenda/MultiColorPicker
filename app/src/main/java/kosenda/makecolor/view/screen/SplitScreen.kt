package kosenda.makecolor.view.screen

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
import kosenda.makecolor.R
import kosenda.makecolor.core.data.default.defaultCategories
import kosenda.makecolor.core.ui.code.ColorIndex
import kosenda.makecolor.core.ui.code.SplitColorNum
import kosenda.makecolor.view.PreviewSurface
import kosenda.makecolor.view.component.ContentDivider
import kosenda.makecolor.view.component.GoogleAd
import kosenda.makecolor.view.component.button.TonalButton
import kosenda.makecolor.view.component.card.SpinnerAndColorCard
import kosenda.makecolor.view.component.card.SpinnerCard
import kosenda.makecolor.view.component.topbar.TopBar
import kosenda.makecolor.view.navigation.NavigationItems
import kosenda.makecolor.view.navigation.SelectColorParam
import kosenda.makecolor.view.navigation.SplitColorParam
import kosenda.makecolor.view.theme.MakeColorTheme
import kosenda.makecolor.viewmodel.PreviewSplitViewModel
import kosenda.makecolor.viewmodel.SplitViewModel
import kosenda.makecolor.viewmodel.SplitViewModelImpl
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun SplitScreen(
    viewModel: SplitViewModelImpl,
    navController: NavController,
    onClickMenu: () -> Unit,
    onClickSelectColor: (SelectColorParam) -> Unit,
) {
    SplitScreenContent(
        viewModel = viewModel,
        navController = navController,
        onClickMenu = onClickMenu,
        onClickSelectColor = onClickSelectColor,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitScreenContent(
    viewModel: SplitViewModel,
    navController: NavController,
    onClickMenu: () -> Unit,
    onClickSelectColor: (SelectColorParam) -> Unit,
    googleAd: @Composable () -> Unit = { GoogleAd() },
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
                        val splitColorParam = SplitColorParam(
                            splitColorNum = uiState.selectSplitColorNum,
                            hex1 = uiState.selectHex1,
                            hex2 = uiState.selectHex2,
                            hex3 = uiState.selectHex3,
                            hex4 = uiState.selectHex4,
                        )
                        navController.navigate(
                            route = "%s/%s".format(
                                NavigationItems.SplitColor.route,
                                Json.encodeToString(splitColorParam),
                            ),
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
                onClickSelectColor = {},
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
                onClickSelectColor = {},
            ) {}
        }
    }
}
