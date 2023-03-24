package kosenda.makecolor.view.screen

import androidx.compose.foundation.background
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kosenda.makecolor.R
import kosenda.makecolor.model.ColorData
import kosenda.makecolor.view.PreviewSurface
import kosenda.makecolor.view.component.card.CategoryNameAndMemoCard
import kosenda.makecolor.view.component.card.ColorValueTextsCard
import kosenda.makecolor.view.component.card.DisplayColorCard
import kosenda.makecolor.view.component.card.TitleCard
import kosenda.makecolor.view.component.topbar.BackTopBar
import kosenda.makecolor.view.theme.MakeColorTheme
import kosenda.makecolor.view.theme.backgroundBrush
import kosenda.makecolor.viewmodel.ColorDetailViewModel
import kosenda.makecolor.viewmodel.ColorDetailViewModelImpl
import kosenda.makecolor.viewmodel.PreviewColorDetailViewModel

@Composable
fun ColorDetailScreen(
    viewModel: ColorDetailViewModelImpl,
    onClickDisplayColor: (ColorData) -> Unit,
    onBackScreen: () -> Unit,
) {
    ColorDetailScreenContent(
        viewModel = viewModel,
        onClickDisplayColor = onClickDisplayColor,
        onBackScreen = onBackScreen,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorDetailScreenContent(
    viewModel: ColorDetailViewModel,
    onClickDisplayColor: (ColorData) -> Unit,
    onBackScreen: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.isNavigationBarVisible = false
    }
    DisposableEffect(Unit) {
        onDispose {
            systemUiController.isNavigationBarVisible = true
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush()),
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
                .padding(it)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(rememberScrollState()),
        ) {
            DisplayColorCard(
                colorData = uiState.colorData,
                onClickDisplayColor = onClickDisplayColor,
            )
            ColorValueTextsCard(colorData = uiState.colorData)

            CategoryNameAndMemoCard(
                categoryName = uiState.categoryName,
                memo = uiState.memo,
            )

            TitleCard(
                text = stringResource(id = R.string.complementary),
                painter = painterResource(id = R.drawable.baseline_color_lens_24),
            )
            DisplayColorCard(
                colorData = uiState.complementaryColorData,
                onClickDisplayColor = onClickDisplayColor,
            )
            ColorValueTextsCard(colorData = uiState.complementaryColorData)

            TitleCard(
                text = stringResource(id = R.string.opposite),
                painter = painterResource(id = R.drawable.baseline_color_lens_24),
            )
            DisplayColorCard(
                colorData = uiState.oppositeColorData,
                onClickDisplayColor = onClickDisplayColor,
            )
            ColorValueTextsCard(colorData = uiState.oppositeColorData)

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewColorDetailScreenContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        PreviewSurface {
            ColorDetailScreenContent(
                viewModel = PreviewColorDetailViewModel(),
                onClickDisplayColor = {},
                onBackScreen = {},
            )
        }
    }
}

@Preview
@Composable
private fun PreviewColorDetailScreenContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        PreviewSurface {
            ColorDetailScreenContent(
                viewModel = PreviewColorDetailViewModel(),
                onClickDisplayColor = {},
                onBackScreen = {},
            )
        }
    }
}
