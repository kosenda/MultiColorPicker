package kosenda.makecolor.core.ui.feature.common.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kosenda.makecolor.core.ui.data.LocalIsExpandScreenClass
import kosenda.makecolor.core.ui.feature.common.button.CustomIconButton
import kosenda.makecolor.core.ui.feature.common.button.ShareIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onClickMenu: () -> Unit,
    onClickInfo: () -> Unit,
    hex1: String? = null,
    hex2: String? = null,
) {
    val isExpandSizeClass = LocalIsExpandScreenClass.current
    TopAppBar(
        title = {},
        navigationIcon = {
            if (isExpandSizeClass) return@TopAppBar
            CustomIconButton(
                imageVector = Icons.Outlined.Menu,
                contentDescription = "open menu",
                onClick = onClickMenu,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
        ),
        scrollBehavior = scrollBehavior,
        actions = {
            ShareIcon(hex1 = hex1, hex2 = hex2)
            CustomIconButton(
                imageVector = Icons.Outlined.Info,
                contentDescription = "open info",
                onClick = onClickInfo,
            )
        },
    )
}
