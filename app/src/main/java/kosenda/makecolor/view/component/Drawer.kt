package kosenda.makecolor.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kosenda.makecolor.R
import kosenda.makecolor.view.DrawerItem
import kosenda.makecolor.view.navigation.NavigationItems
import kosenda.makecolor.view.theme.backgroundBrush
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    drawerState: DrawerState,
    navController: NavController,
) {
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
            .background(brush = backgroundBrush())
            .padding(start = 4.dp, end = 4.dp, top = 48.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
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
                                launchSingleTop = true
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
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 8.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = "developed by KSND  2022",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
