package kosenda.makecolor.view.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kosenda.makecolor.core.ui.feature.common.LocalIsDark
import kosenda.makecolor.core.ui.feature.theme.backgroundBottomColor
import kosenda.makecolor.core.ui.feature.theme.backgroundBrush
import kosenda.makecolor.core.ui.feature.theme.backgroundTopColor
import kosenda.makecolor.view.Drawer
import kosenda.makecolor.view.navigation.Navigation
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FirstScreen() {
    val systemUiController = rememberSystemUiController()
    val topColor = backgroundTopColor()
    val bottomColor = backgroundBottomColor()
    val isDark = LocalIsDark.current
    SideEffect {
        systemUiController.setStatusBarColor(
            color = topColor,
            darkIcons = isDark.not(),
        )
        systemUiController.setNavigationBarColor(color = bottomColor)
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberAnimatedNavController()

    Surface(
        modifier = Modifier.background(brush = backgroundBrush()),
        color = Color.Transparent,
    ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier
                        .statusBarsPadding()
                        .navigationBarsPadding(),
                    drawerContainerColor = Color.Transparent,
                ) {
                    Drawer(drawerState = drawerState, navController = navController)
                }
            },
        ) {
            Navigation(
                navController = navController,
                onClickMenu = { coroutineScope.launch { drawerState.open() } },
            )
        }
    }
}
