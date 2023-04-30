package kosenda.makecolor.feature.edit

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.feature.common.button.CustomOutlinedButton
import kosenda.makecolor.core.ui.feature.common.button.CustomTextButton
import kosenda.makecolor.core.ui.feature.common.button.TonalButton
import kosenda.makecolor.core.ui.feature.common.card.ColorValueTextsCard
import kosenda.makecolor.core.ui.feature.common.card.DisplayColorCard
import kosenda.makecolor.core.ui.feature.common.card.SpinnerCard
import kosenda.makecolor.core.ui.feature.common.card.TitleCard
import kosenda.makecolor.core.ui.feature.common.dialog.NewCategoryDialog
import kosenda.makecolor.core.ui.feature.common.textfield.EnterFiled
import kosenda.makecolor.core.ui.feature.common.topbar.BackTopBar
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.feature.preview.PreviewSurface

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModelImpl,
    onBackScreen: () -> Unit,
) {
    RegisterScreenContent(
        viewModel = viewModel,
        onBackScreen = onBackScreen,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenContent(
    viewModel: RegisterViewModel,
    onBackScreen: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    if (uiState.isShowNewCategoryDialog) {
        NewCategoryDialog(
            onClickNew = viewModel::addCategory,
            onClose = viewModel::closeAddCategoryDialog,
        )
    }

    LaunchedEffect(uiState.showToast) {
        if (uiState.showToast) {
            Toast.makeText(context, R.string.registrated, Toast.LENGTH_SHORT).show()
            viewModel.clearShowToast()
        }
    }

    Scaffold(
        topBar = { BackTopBar(onClick = onBackScreen, scrollBehavior = scrollBehavior) },
        containerColor = Color.Transparent,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusManager.clearFocus()
                        },
                    )
                },
        ) {
            TitleCard(
                text = stringResource(id = R.string.registration),
                painter = painterResource(id = R.drawable.baseline_palette_24),
            )
            DisplayColorCard(
                colorData = uiState.colorData,
                isTappable = false,
            )
            ColorValueTextsCard(
                colorData = uiState.colorData,
                withCopyImage = false,
            )

            TitleCard(
                text = stringResource(id = R.string.category),
                painter = painterResource(id = R.drawable.baseline_category_24),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp),
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                ) {
                    if (uiState.categories.isEmpty().not()) {
                        SpinnerCard(
                            modifier = Modifier.padding(start = 8.dp),
                            selectedText = uiState.selectCategory.name,
                            categoryName = stringResource(id = R.string.select),
                            onSelectedChange = viewModel::changeSelectCategory,
                            displayItemList = uiState.displayCategories.toTypedArray(),
                        )
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                CustomOutlinedButton(
                    text = stringResource(id = R.string.add),
                    painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                    onClick = viewModel::openAddCategoryDialog,
                )
            }

            TitleCard(
                text = stringResource(id = R.string.memo),
                painter = painterResource(id = R.drawable.ic_baseline_edit_24),
            )
            EnterFiled(
                text = uiState.memo,
                onValueChange = viewModel::updateMemo,
                focusManager = focusManager,
                hint = stringResource(id = R.string.enter_memo),
            )
            Row(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 48.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                CustomTextButton(
                    text = stringResource(id = R.string.cancel),
                    onClick = onBackScreen,
                )
                TonalButton(
                    text = stringResource(id = R.string.registration),
                    painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    height = 60.dp,
                    onClick = {
                        viewModel.registerColor(hex = uiState.colorData.hex.toString())
                        onBackScreen()
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRegisterScreenContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        PreviewSurface {
            RegisterScreenContent(
                viewModel = PreviewRegisterViewModel(),
                onBackScreen = {},
            )
        }
    }
}

@Preview
@Composable
private fun PreviewRegisterScreenContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        PreviewSurface {
            RegisterScreenContent(
                viewModel = PreviewRegisterViewModel(),
                onBackScreen = {},
            )
        }
    }
}
