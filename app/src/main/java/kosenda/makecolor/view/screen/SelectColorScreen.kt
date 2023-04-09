package kosenda.makecolor.view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kosenda.makecolor.R
import kosenda.makecolor.core.model.data.ColorItem
import kosenda.makecolor.core.ui.feature.common.button.TonalButton
import kosenda.makecolor.core.ui.feature.common.card.ColorCard
import kosenda.makecolor.core.ui.feature.common.topbar.SelectColorTopBar
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.core.util.getNameIfNoAlias
import kosenda.makecolor.feature.preview.PreviewSurface
import kosenda.makecolor.view.navigation.CategoryDetailParam
import kosenda.makecolor.viewmodel.PreviewSelectColorViewModel
import kosenda.makecolor.viewmodel.SelectColorViewModel
import kosenda.makecolor.viewmodel.SelectColorViewModelImpl

@Composable
fun SelectColorScreen(
    viewModel: SelectColorViewModelImpl,
    backScreenWithSelectHex: (String, Int) -> Unit,
    goToColorDetail: (ColorItem, String) -> Unit,
    goToCategoryDetail: (CategoryDetailParam) -> Unit,
    onBackScreen: () -> Unit,
) {
    SelectColorScreenContent(
        viewModel = viewModel,
        backScreenWithSelectHex = backScreenWithSelectHex,
        goToColorDetail = goToColorDetail,
        goToCategoryDetail = goToCategoryDetail,
        onBackScreen = onBackScreen,
    )
}

@Composable
fun SelectColorScreenContent(
    viewModel: SelectColorViewModel,
    backScreenWithSelectHex: (String, Int) -> Unit,
    goToColorDetail: (ColorItem, String) -> Unit,
    goToCategoryDetail: (CategoryDetailParam) -> Unit,
    onBackScreen: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    var topContentHeight by rememberSaveable { mutableStateOf(0f) }
    var topContentTopPosition by rememberSaveable { mutableStateOf(0f) }
    val topContentBackgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
    val density = LocalDensity.current.density
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(Color.Transparent)
    }

    LaunchedEffect(Unit) {
        viewModel.getColors()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box {
            if (uiState.colors.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Spacer(
                            modifier = Modifier.height(
                                height = topContentTopPosition.dp + topContentHeight.dp,
                            ),
                        )
                    }
                    items(
                        items = uiState.colors,
                        key = { colorItem -> colorItem.id },
                        itemContent = { colorItem ->
                            ColorCard(
                                colorItem = colorItem,
                                onClick = {
                                    when {
                                        uiState.needBack -> {
                                            backScreenWithSelectHex(
                                                colorItem.hex,
                                                uiState.index,
                                            )
                                        }
                                        else -> {
                                            goToColorDetail(
                                                colorItem,
                                                uiState.category.getNameIfNoAlias(),
                                            )
                                        }
                                    }
                                },
                                onDelete = when {
                                    uiState.needBack -> null
                                    colorItem.category == "_" -> null
                                    else -> viewModel::deleteColorItem
                                },
                            )
                        },
                    )
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(id = R.string.not_data),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }

            SelectColorTopBar(
                modifier = Modifier.onGloballyPositioned {
                    topContentHeight = it.size.height / density
                    topContentTopPosition = it.positionInWindow().y / density
                },
                backgroundColor = topContentBackgroundColor,
                categoryName = uiState.category.getNameIfNoAlias(),
                onBackScreen = onBackScreen,
                detailButton = if (uiState.needBack) {
                    null
                } else { {
                    TonalButton(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = stringResource(id = R.string.detail),
                        onClick = {
                            val isDefault = uiState.colors.firstOrNull()?.category == "_"
                            goToCategoryDetail(
                                CategoryDetailParam(
                                    colors = uiState.colors,
                                    category = uiState.category,
                                    isDefault = isDefault,
                                ),
                            )
                        },
                    )
                }
                },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSelectColorScreenContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        PreviewSurface {
            SelectColorScreenContent(
                viewModel = PreviewSelectColorViewModel(),
                backScreenWithSelectHex = { _, _ -> },
                goToColorDetail = { _, _ -> },
                goToCategoryDetail = {},
                onBackScreen = {},
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSelectColorScreenContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        PreviewSurface {
            SelectColorScreenContent(
                viewModel = PreviewSelectColorViewModel(),
                backScreenWithSelectHex = { _, _ -> },
                goToColorDetail = { _, _ -> },
                goToCategoryDetail = {},
                onBackScreen = {},
            )
        }
    }
}
