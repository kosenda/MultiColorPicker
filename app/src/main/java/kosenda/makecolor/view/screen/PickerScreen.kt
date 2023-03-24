package kosenda.makecolor.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import kosenda.makecolor.R
import kosenda.makecolor.model.ColorData
import kosenda.makecolor.view.PreviewSurface
import kosenda.makecolor.view.code.PickerType
import kosenda.makecolor.view.component.GoogleAd
import kosenda.makecolor.view.component.button.FloatingAddButton
import kosenda.makecolor.view.component.card.ClassicColorPickerCard
import kosenda.makecolor.view.component.card.ColorValueTextsCard
import kosenda.makecolor.view.component.card.DisplayColorCard
import kosenda.makecolor.view.component.card.HarmonyColorPickerCard
import kosenda.makecolor.view.component.card.SpinnerCard
import kosenda.makecolor.view.component.topbar.TopBar
import kosenda.makecolor.view.theme.MakeColorTheme
import kosenda.makecolor.viewmodel.PickerViewModel
import kosenda.makecolor.viewmodel.PickerViewModelImpl
import kosenda.makecolor.viewmodel.PreviewPickerViewModel

@Composable
fun PickerScreen(
    viewModel: PickerViewModelImpl,
    onClickMenu: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
) {
    PickerScreenContent(
        viewModel = viewModel,
        onClickMenu = onClickMenu,
        onClickFloatingButton = onClickFloatingButton,
        onClickDisplayColor = onClickDisplayColor,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickerScreenContent(
    viewModel: PickerViewModel,
    onClickMenu: () -> Unit,
    onClickFloatingButton: (ColorData) -> Unit,
    onClickDisplayColor: (ColorData) -> Unit,
    googleAd: @Composable () -> Unit = { GoogleAd() },
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val horizontalPadding = 16.dp

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
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = horizontalPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(scrollState),
        ) {
            DisplayColorCard(
                colorData = uiState.colorData,
                onClickDisplayColor = onClickDisplayColor,
            )
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
            ) {
                SpinnerCard(
                    selectedText = when (uiState.pickerType) {
                        PickerType.CIRCLE -> stringArrayResource(id = R.array.square_circle)[0]
                        PickerType.SQUARE -> stringArrayResource(id = R.array.square_circle)[1]
                    },
                    categoryName = stringResource(id = R.string.picker),
                    onSelectedChange = { index ->
                        when (index) {
                            0 -> viewModel.updatePickerType(type = PickerType.CIRCLE)
                            else -> viewModel.updatePickerType(type = PickerType.SQUARE)
                        }
                    },
                    displayItemList = stringArrayResource(id = R.array.square_circle),
                )

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.defaultMinSize(20.dp))

                if (uiState.pickerType == PickerType.CIRCLE) {
                    SpinnerCard(
                        selectedText = stringArrayResource(id = R.array.color_harmony_mode).get(
                            index = when (uiState.harmonyMode) {
                                ColorHarmonyMode.ANALOGOUS -> 0
                                ColorHarmonyMode.MONOCHROMATIC -> 1
                                ColorHarmonyMode.COMPLEMENTARY -> 2
                                ColorHarmonyMode.SPLIT_COMPLEMENTARY -> 3
                                ColorHarmonyMode.TRIADIC -> 4
                                ColorHarmonyMode.TETRADIC -> 5
                                ColorHarmonyMode.SHADES -> 6
                                else -> throw IllegalArgumentException()
                            },
                        ),
                        categoryName = stringResource(id = R.string.mode),
                        onSelectedChange = { index ->
                            viewModel.updateHarmonyMode(
                                mode = when (index) {
                                    0 -> ColorHarmonyMode.ANALOGOUS
                                    1 -> ColorHarmonyMode.MONOCHROMATIC
                                    2 -> ColorHarmonyMode.COMPLEMENTARY
                                    3 -> ColorHarmonyMode.SPLIT_COMPLEMENTARY
                                    4 -> ColorHarmonyMode.TRIADIC
                                    5 -> ColorHarmonyMode.TETRADIC
                                    6 -> ColorHarmonyMode.SHADES
                                    else -> throw IllegalArgumentException()
                                },
                            )
                        },
                        displayItemList = stringArrayResource(id = R.array.color_harmony_mode),
                    )
                }
            }

            when (uiState.pickerType) {
                PickerType.CIRCLE -> {
                    HarmonyColorPickerCard(
                        defaultColor = uiState.defaultColor,
                        hsvColorData = uiState.hsvColorData,
                        colorHarmonyMode = uiState.harmonyMode,
                        horizontalPadding = horizontalPadding,
                        onClickColorCircle = viewModel::updateColorData,
                        onColorChanged = viewModel::updateHsvColor,
                    )
                }
                PickerType.SQUARE -> {
                    ClassicColorPickerCard(
                        defaultColor = uiState.defaultColor,
                        onColorChange = viewModel::updateColorData,
                    )
                }
            }

            ColorValueTextsCard(colorData = uiState.colorData)
            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewPickerScreenContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        PreviewSurface {
            PickerScreenContent(
                viewModel = PreviewPickerViewModel(),
                onClickMenu = {},
                onClickFloatingButton = {},
                onClickDisplayColor = {},
            ) {}
        }
    }
}

@Preview
@Composable
private fun PreviewPickerScreenContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        PreviewSurface {
            PickerScreenContent(
                viewModel = PreviewPickerViewModel(),
                onClickMenu = {},
                onClickFloatingButton = {},
                onClickDisplayColor = {},
            ) {}
        }
    }
}
