package kosenda.makecolor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.data.LocalIsExpandScreenClass
import kosenda.makecolor.core.ui.feature.theme.backgroundBrush
import kosenda.makecolor.navigation.NavigationItems
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    drawerState: DrawerState,
    navController: NavController,
) {
    val isExpandScreenClass = LocalIsExpandScreenClass.current
    var selectItemRoute by rememberSaveable { mutableStateOf(NavigationItems.Picker.route) }
    val uriHandler = LocalUriHandler.current
    val reviewURL = stringResource(id = R.string.review_url)
    val coroutineScope = rememberCoroutineScope()
    val displayNavigationItems = listOf(
        NavigationItems.Picker,
        NavigationItems.Seekbar,
        NavigationItems.Text,
        NavigationItems.Picture,
        NavigationItems.Merge,
        NavigationItems.Random,
        NavigationItems.Data,
        NavigationItems.Split,
        NavigationItems.Gradation,
        NavigationItems.Review,
        NavigationItems.Setting,
    )

    Column(
        modifier = Modifier
            .then(
                if (isExpandScreenClass) {
                    Modifier.background(color = Color.Transparent)
                } else {
                    Modifier.background(brush = backgroundBrush())
                },
            )
            .fillMaxHeight()
            .wrapContentWidth()
            .verticalScroll(rememberScrollState())
            .padding(all = 8.dp),
    ) {
        displayNavigationItems.map { item ->
            DrawerItem(
                item = item,
                isSelected = selectItemRoute == item.route,
                onItemClick = { clickItem ->
                    when (clickItem) {
                        NavigationItems.Review -> uriHandler.openUri(uri = reviewURL)
                        else -> {
                            selectItemRoute = clickItem.route
                            navController.navigate(clickItem.route) {
                                popUpTo(0) { saveState = true }
                                launchSingleTop = false
                                restoreState = false
                            }
                        }
                    }
                    coroutineScope.launch {
                        drawerState.close()
                    }
                },
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier
                .then(if (isExpandScreenClass) Modifier else Modifier.fillMaxWidth())
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp, bottom = 8.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = "developed by KSND  2023",
            modifier = Modifier
                .then(if (isExpandScreenClass) Modifier else Modifier.fillMaxWidth())
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
