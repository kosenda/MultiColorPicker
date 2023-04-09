package kosenda.makecolor.core.ui.feature.common.topbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.ui.R
import kosenda.makecolor.core.ui.feature.common.button.BackButton
import kosenda.makecolor.core.ui.feature.common.button.TonalButton
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.core.ui.feature.theme.backgroundTopColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectColorTopBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    categoryName: String,
    onBackScreen: () -> Unit,
    detailButton: (@Composable () -> Unit)? = null,
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            Row(
                modifier = modifier
                    .statusBarsPadding()
                    .wrapContentHeight()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BackButton(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClickBack = onBackScreen,
                )
                Column(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .weight(1f),
                ) {
                    Text(
                        text = stringResource(id = R.string.category),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                    Text(
                        text = categoryName,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                if (detailButton != null) {
                    detailButton()
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor),
    )
}

@Preview
@Composable
fun PreviewSelectColorTopBar() {
    MakeColorTheme(isDarkTheme = false) {
        SelectColorTopBar(
            backgroundColor = backgroundTopColor(),
            categoryName = "Category1",
            onBackScreen = {},
        ) {
            TonalButton(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = stringResource(id = R.string.detail),
                onClick = {},
            )
        }
    }
}
