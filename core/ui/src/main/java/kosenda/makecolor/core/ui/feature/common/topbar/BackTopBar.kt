package kosenda.makecolor.core.ui.feature.common.topbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.ui.feature.common.button.BackButton
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onClick: () -> Unit,
) {
    TopAppBar(
        title = {},
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            BackButton(
                modifier = Modifier.padding(start = 16.dp),
                onClickBack = onClick,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewBackTopBar_Light() {
    MakeColorTheme(isDarkTheme = false) {
        BackTopBar(TopAppBarDefaults.pinnedScrollBehavior()) {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewBackTopBar_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        BackTopBar(TopAppBarDefaults.pinnedScrollBehavior()) {}
    }
}
