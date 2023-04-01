package kosenda.makecolor.view.component.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kosenda.makecolor.view.component.button.CustomIconButton
import kosenda.makecolor.view.component.button.ShareIcon
import kosenda.makecolor.view.dialog.InfoDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onClickMenu: () -> Unit,
    hex1: String? = null,
    hex2: String? = null,
) {
    var isShowInfoDialog by rememberSaveable { mutableStateOf(false) }

    if (isShowInfoDialog) {
        InfoDialog(onClose = { isShowInfoDialog = false })
    }

    TopAppBar(
        title = {},
        navigationIcon = {
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
                onClick = { isShowInfoDialog = true },
            )
        },
    )
}
