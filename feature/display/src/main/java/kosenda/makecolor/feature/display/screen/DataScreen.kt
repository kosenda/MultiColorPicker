package kosenda.makecolor.feature.display.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kosenda.makecolor.core.data.default.defaultCategories
import kosenda.makecolor.core.ui.data.ColorIndex
import kosenda.makecolor.core.ui.feature.common.SelectColorParam
import kosenda.makecolor.core.ui.feature.common.card.CategoryCard
import kosenda.makecolor.core.ui.feature.common.topbar.TopBar
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.core.ui.feature.theme.backgroundBottomColor
import kosenda.makecolor.core.util.getNameIfNoAlias
import kosenda.makecolor.feature.display.viewmodel.DataViewModel
import kosenda.makecolor.feature.display.viewmodel.DataViewModelImpl
import kosenda.makecolor.feature.display.viewmodel.PreviewDataViewModel
import kosenda.makecolor.feature.preview.PreviewSurface

@Composable
fun DataScreen(
    viewModel: DataViewModelImpl,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickSelectColor: (SelectColorParam) -> Unit,
    googleAd: @Composable () -> Unit = {},
) {
    DataScreenContent(
        viewModel = viewModel,
        onClickMenu = onClickMenu,
        onClickInfo = onClickInfo,
        onClickSelectColor = onClickSelectColor,
        googleAd = googleAd,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreenContent(
    viewModel: DataViewModel,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    onClickSelectColor: (SelectColorParam) -> Unit,
    googleAd: @Composable () -> Unit = {},
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val systemUiController = rememberSystemUiController()
    val backgroundBottomColor = backgroundBottomColor()

    SideEffect {
        systemUiController.setNavigationBarColor(color = backgroundBottomColor)
    }

    LaunchedEffect(Unit) {
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
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        ) {
            LazyColumn {
                items(
                    items = uiState.categories,
                    itemContent = { category ->
                        CategoryCard(
                            category = category.getNameIfNoAlias(),
                            size = category.size,
                            onClick = {
                                onClickSelectColor(
                                    SelectColorParam(
                                        category = category,
                                        index = ColorIndex.FIRST,
                                        needBack = false,
                                    ),
                                )
                            },
                        )
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDataScreen_Light() {
    MakeColorTheme(isDarkTheme = false) {
        PreviewSurface {
            DataScreenContent(
                viewModel = PreviewDataViewModel(),
                onClickMenu = {},
                onClickInfo = {},
                onClickSelectColor = {},
                googleAd = {},
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDataScreen_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        PreviewSurface {
            DataScreenContent(
                viewModel = PreviewDataViewModel(),
                onClickMenu = {},
                onClickInfo = {},
                onClickSelectColor = {},
                googleAd = {},
            )
        }
    }
}
