package kosenda.makecolor

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kosenda.makecolor.core.ui.data.LocalIsExpandScreenClass
import kosenda.makecolor.core.ui.feature.common.LocalIsDark
import kosenda.makecolor.core.ui.feature.theme.backgroundBottomColor
import kosenda.makecolor.core.ui.feature.theme.backgroundBrush
import kosenda.makecolor.core.ui.feature.theme.backgroundTopColor
import kosenda.makecolor.navigation.Navigation
import kosenda.makecolor.ui.Drawer
import kosenda.makecolor.ui.GoogleAd
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
    val isExpandScreenClass = LocalIsExpandScreenClass.current

    val navigation: @Composable () -> Unit = {
        Navigation(
            navController = navController,
            onClickMenu = { coroutineScope.launch { drawerState.open() } },
        )
    }

    val modalDrawerSheet: @Composable () -> Unit = {
        ModalDrawerSheet(
            modifier = Modifier,
            drawerContainerColor = Color.Transparent,
        ) {
            Drawer(drawerState = drawerState, navController = navController)
        }
    }

    Surface(
        modifier = Modifier.background(brush = backgroundBrush()),
        color = Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .displayCutoutPadding()
                .systemBarsPadding()
                .background(brush = backgroundBrush())
                .fillMaxSize(),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                if (isExpandScreenClass) {
                    PermanentNavigationDrawer(
                        drawerContent = modalDrawerSheet,
                        content = navigation,
                    )
                } else {
                    ModalNavigationDrawer(
                        drawerContent = modalDrawerSheet,
                        drawerState = drawerState,
                        content = navigation,
                    )
                }
            }
            GoogleAd()
        }
    }
}
