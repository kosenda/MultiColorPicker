package kosenda.makecolor.feature.display.screen

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kosenda.makecolor.core.data.default.defaultCategories
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.data.ColorIndex
import kosenda.makecolor.core.ui.feature.common.SelectColorParam
import kosenda.makecolor.core.ui.feature.common.card.DisplayGradationColorCard
import kosenda.makecolor.core.ui.feature.common.card.HexAndDisplayColorCard
import kosenda.makecolor.core.ui.feature.common.card.SpinnerCard
import kosenda.makecolor.core.ui.feature.common.topbar.TopBar
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.core.util.getNameIfNoAlias
import kosenda.makecolor.feature.display.viewmodel.GradationViewModel
import kosenda.makecolor.feature.display.viewmodel.GradationViewModelImpl
import kosenda.makecolor.feature.display.viewmodel.PreviewGradationViewModel
import kosenda.makecolor.feature.preview.PreviewSurface

@Composable
fun GradationScreen(
    viewModel: GradationViewModelImpl,
    navController: NavController,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickDisplayGradationColor: (String, String) -> Unit,
    onClickSelectColor: (SelectColorParam) -> Unit,
) {
    GradationScreenContent(
        viewModel = viewModel,
        navController = navController,
        onClickMenu = onClickMenu,
        onClickInfo = onClickInfo,
        onClickDisplayGradationColor = onClickDisplayGradationColor,
        onClickSelectColor = onClickSelectColor,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradationScreenContent(
    viewModel: GradationViewModel,
    navController: NavController,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickDisplayGradationColor: (String, String) -> Unit,
    onClickSelectColor: (SelectColorParam) -> Unit,
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
                hex1 = uiState.selectHex1,
                hex2 = uiState.selectHex2,
            )
        },
        containerColor = Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(horizontal = 16.dp),
        ) {
            DisplayGradationColorCard(
                leftHex = uiState.selectHex1,
                rightHex = uiState.selectHex2,
                onClickDisplayGradationColor = onClickDisplayGradationColor,
            )

            SpinnerCard(
                modifier = Modifier.padding(top = 8.dp),
                selectedText = uiState.selectCategory1.getNameIfNoAlias(),
                categoryName = stringResource(id = R.string.select_1),
                onSelectedChange = viewModel::updateSelectCategory1,
                displayItemList = uiState.displayCategories.toTypedArray(),
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
                displayItemList = uiState.displayCategories.toTypedArray(),
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

            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewGradationScreen_Light() {
    MakeColorTheme(isDarkTheme = false) {
        PreviewSurface {
            GradationScreenContent(
                viewModel = PreviewGradationViewModel(),
                navController = rememberNavController(),
                onClickMenu = {},
                onClickInfo = {},
                onClickDisplayGradationColor = { _, _ -> },
                onClickSelectColor = {},
            )
        }
    }
}

@Preview
@Composable
private fun PreviewGradationScreen_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        PreviewSurface {
            GradationScreenContent(
                viewModel = PreviewGradationViewModel(),
                navController = rememberNavController(),
                onClickMenu = {},
                onClickInfo = {},
                onClickDisplayGradationColor = { _, _ -> },
                onClickSelectColor = {},
            )
        }
    }
}
