package kosenda.makecolor.feature.display.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.feature.common.button.DeleteButton
import kosenda.makecolor.core.ui.feature.common.button.TonalButton
import kosenda.makecolor.core.ui.feature.common.dialog.NewCategoryDialog
import kosenda.makecolor.core.ui.feature.common.topbar.BackTopBar
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.core.util.getNameIfNoAlias
import kosenda.makecolor.core.util.hexToColor
import kosenda.makecolor.feature.display.viewmodel.CategoryDetailViewModel
import kosenda.makecolor.feature.display.viewmodel.CategoryDetailViewModelImpl
import kosenda.makecolor.feature.display.viewmodel.PreviewCategoryDetailViewModel
import kosenda.makecolor.feature.preview.PreviewSurface

@Composable
fun CategoryDetailScreen(
    viewModel: CategoryDetailViewModelImpl,
    onBackScreen: () -> Unit,
    onBackDataScreen: () -> Unit,
) {
    CategoryDetailScreenContent(
        viewModel = viewModel,
        onBackScreen = onBackScreen,
        onBackDataScreen = onBackDataScreen,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailScreenContent(
    viewModel: CategoryDetailViewModel,
    onBackScreen: () -> Unit,
    onBackDataScreen: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val horizontalPadding = 16.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp - horizontalPadding * 2
    val columnSize = uiState.colors.size / 10 + 1
    val colorBoxSize = screenWidth / 10
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current
    val toastDeleteText = stringResource(id = R.string.deleted)
    val toastChangedText = stringResource(id = R.string.registrated)

    if (uiState.isShowNewCategoryDialog) {
        NewCategoryDialog(
            oldCategories = Category(name = uiState.category.name, size = -1),
            onClickNew = { categories ->
                viewModel.updateCategory(categories)
                Toast.makeText(context, toastChangedText, Toast.LENGTH_SHORT).show()
                onBackDataScreen()
            },
            onClose = viewModel::closeNewCategoryDialog,
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BackTopBar(
                scrollBehavior = scrollBehavior,
                onClick = onBackScreen,
            )
        },
        containerColor = Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = horizontalPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp),
                text = stringResource(id = R.string.category),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Text(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, bottom = 24.dp),
                text = uiState.category.getNameIfNoAlias(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            if (uiState.isDefault.not()) {
                Row(
                    modifier = Modifier.padding(bottom = 24.dp),
                ) {
                    TonalButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(id = R.string.change),
                        onClick = viewModel::openNewCategoryDialog,
                    )
                    DeleteButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            viewModel.deleteCategory()
                            Toast.makeText(context, toastDeleteText, Toast.LENGTH_SHORT).show()
                            onBackDataScreen()
                        },
                    )
                }
            }
            repeat(times = columnSize) { columnIndex ->
                Row {
                    uiState.colors
                        .filterIndexed { index, _ ->
                            (columnIndex) * 10 < index + 1 &&
                                (columnIndex + 1) * 10 > index &&
                                index < uiState.colors.size
                        }
                        .map { colorItem ->
                            Box(
                                modifier = Modifier
                                    .size(colorBoxSize)
                                    .padding(1.dp)
                                    .background(hexToColor(colorItem.hex)),
                            )
                        }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCategoryDetailScreenContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        PreviewSurface {
            CategoryDetailScreenContent(
                viewModel = PreviewCategoryDetailViewModel(),
                onBackScreen = {},
                onBackDataScreen = {},
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCategoryDetailScreenContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        PreviewSurface {
            CategoryDetailScreenContent(
                viewModel = PreviewCategoryDetailViewModel(),
                onBackScreen = {},
                onBackDataScreen = {},
            )
        }
    }
}
