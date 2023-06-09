package kosenda.makecolor.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kosenda.makecolor.core.model.data.CategoryDetailParam
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.model.data.ColorItem
import kosenda.makecolor.core.ui.data.NavKey
import kosenda.makecolor.core.ui.data.SplitColorParam
import kosenda.makecolor.core.ui.data.navArg
import kosenda.makecolor.core.ui.feature.common.SelectColorParam
import kosenda.makecolor.feature.display.screen.CategoryDetailScreen
import kosenda.makecolor.feature.display.screen.ColorDetailScreen
import kosenda.makecolor.feature.display.screen.DataScreen
import kosenda.makecolor.feature.display.screen.FullColorScreen
import kosenda.makecolor.feature.display.screen.GradationColorScreen
import kosenda.makecolor.feature.display.screen.GradationScreen
import kosenda.makecolor.feature.display.screen.SelectColorScreen
import kosenda.makecolor.feature.display.screen.SplitColorScreen
import kosenda.makecolor.feature.display.screen.SplitScreen
import kosenda.makecolor.feature.edit.RegisterScreen
import kosenda.makecolor.feature.info.InfoDialog
import kosenda.makecolor.feature.screen.InputTextScreen
import kosenda.makecolor.feature.screen.MergeScreen
import kosenda.makecolor.feature.screen.PickerScreen
import kosenda.makecolor.feature.screen.PictureScreen
import kosenda.makecolor.feature.screen.RandomScreen
import kosenda.makecolor.feature.screen.SeekbarScreen
import kosenda.makecolor.feature.settings.SettingScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    onClickMenu: () -> Unit,
) {
    val isShowInfoDialog = rememberSaveable { mutableStateOf(false) }
    val animationSpec: FiniteAnimationSpec<Float> = tween(durationMillis = 400)

    fun openInfoDialog() {
        isShowInfoDialog.value = true
    }

    if (isShowInfoDialog.value) {
        InfoDialog(onClose = { isShowInfoDialog.value = false })
    }

    fun NavGraphBuilder.horizontalComposable(
        route: String,
        arguments: List<NamedNavArgument> = emptyList(),
        content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
    ) {
        composable(
            route = route,
            arguments = arguments,
            enterTransition = { fadeIn(animationSpec) + slideInHorizontally() },
            exitTransition = { fadeOut(animationSpec) + slideOutHorizontally() },
            content = content,
        )
    }

    fun NavGraphBuilder.fadeComposable(
        route: String,
        arguments: List<NamedNavArgument> = emptyList(),
        content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
    ) {
        composable(
            route = route,
            arguments = arguments,
            enterTransition = { fadeIn(animationSpec) },
            exitTransition = { fadeOut(animationSpec) },
            content = content,
        )
    }

    fun onBackScreen() = navController.navigateUp()

    fun onClickDisplayColor(colorData: ColorData) {
        navController.navigate(
            route = "%s/%s".format(
                NavigationItems.FullColor.route,
                Json.encodeToString(colorData),
            ),
        )
    }

    fun onClickDisplayGradationColor(leftHex: String, rightHex: String) {
        navController.navigate(
            route = "%s/%s/%s".format(
                NavigationItems.GradationColor.route,
                leftHex,
                rightHex,
            ),
        )
    }

    fun onClickFloatingButton(colorData: ColorData) {
        navController.navigate(
            route = "%s/%s".format(
                NavigationItems.Register.route,
                Json.encodeToString(colorData),
            ),
        )
    }

    fun onClickSelectColor(selectColorParam: SelectColorParam) {
        navController.navigate(
            route = "%s/%s/%d/%b".format(
                NavigationItems.SelectColor.route,
                Json.encodeToString(selectColorParam.category),
                selectColorParam.index.num,
                selectColorParam.needBack,
            ),
        )
    }

    fun onClickSplitColor(splitColorParam: SplitColorParam) {
        navController.navigate(
            route = "%s/%s".format(
                NavigationItems.SplitColor.route,
                Json.encodeToString(splitColorParam),
            ),
        )
    }

    fun backScreenWithSelectHex(newHex: String, index: Int) {
        navController.previousBackStackEntry?.run {
            savedStateHandle["newHex"] = newHex
            savedStateHandle["index"] = index
        }
        onBackScreen()
    }

    fun goToColorDetail(colorItem: ColorItem, categoryName: String) {
        navController.navigate(
            route = "%s/%s/%s".format(
                NavigationItems.ColorDetail.route,
                Json.encodeToString(colorItem),
                categoryName,
            ),
        )
    }

    fun goToCategoryDetail(categoryDetailParam: CategoryDetailParam) {
        navController.navigate(
            route = "%s/%s".format(
                NavigationItems.CategoryDetail.route,
                Json.encodeToString(categoryDetailParam),
            ),
        )
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = NavigationItems.Picker.route,
    ) {
        horizontalComposable(NavigationItems.Picker.route) {
            PickerScreen(
                viewModel = hiltViewModel(),
                onClickMenu = onClickMenu,
                onClickInfo = ::openInfoDialog,
                onClickDisplayColor = ::onClickDisplayColor,
                onClickFloatingButton = ::onClickFloatingButton,
            )
        }
        horizontalComposable(NavigationItems.Seekbar.route) {
            SeekbarScreen(
                viewModel = hiltViewModel(),
                onClickMenu = onClickMenu,
                onClickInfo = ::openInfoDialog,
                onClickDisplayColor = ::onClickDisplayColor,
                onClickFloatingButton = ::onClickFloatingButton,
            )
        }
        horizontalComposable(NavigationItems.Text.route) {
            InputTextScreen(
                viewModel = hiltViewModel(),
                onClickMenu = onClickMenu,
                onClickInfo = ::openInfoDialog,
                onClickDisplayColor = ::onClickDisplayColor,
                onClickFloatingButton = ::onClickFloatingButton,
            )
        }
        horizontalComposable(NavigationItems.Picture.route) {
            PictureScreen(
                viewModel = hiltViewModel(),
                onClickMenu = onClickMenu,
                onClickInfo = ::openInfoDialog,
                onClickDisplayColor = ::onClickDisplayColor,
                onClickFloatingButton = ::onClickFloatingButton,
            )
        }
        horizontalComposable(NavigationItems.Merge.route) {
            MergeScreen(
                navController = navController,
                viewModel = hiltViewModel(),
                onClickMenu = onClickMenu,
                onClickInfo = ::openInfoDialog,
                onClickDisplayColor = ::onClickDisplayColor,
                onClickFloatingButton = ::onClickFloatingButton,
                onClickSelectColor = ::onClickSelectColor,
            )
        }
        horizontalComposable(NavigationItems.Random.route) {
            RandomScreen(
                viewModel = hiltViewModel(),
                onClickMenu = onClickMenu,
                onClickInfo = ::openInfoDialog,
                onClickDisplayColor = ::onClickDisplayColor,
                onClickFloatingButton = ::onClickFloatingButton,
            )
        }
        fadeComposable(NavigationItems.Data.route) {
            DataScreen(
                viewModel = hiltViewModel(),
                onClickMenu = onClickMenu,
                onClickInfo = ::openInfoDialog,
                onClickSelectColor = ::onClickSelectColor,
            )
        }
        horizontalComposable(NavigationItems.Split.route) {
            SplitScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                onClickMenu = onClickMenu,
                onClickInfo = ::openInfoDialog,
                onClickSelectColor = ::onClickSelectColor,
                onClickSplitColor = ::onClickSplitColor,
            )
        }
        horizontalComposable(NavigationItems.Gradation.route) {
            GradationScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                onClickMenu = onClickMenu,
                onClickInfo = ::openInfoDialog,
                onClickDisplayGradationColor = ::onClickDisplayGradationColor,
                onClickSelectColor = ::onClickSelectColor,
            )
        }
        horizontalComposable(NavigationItems.Setting.route) {
            SettingScreen(
                viewModel = hiltViewModel(),
                onClickMenu = onClickMenu,
                onClickInfo = ::openInfoDialog,
            )
        }
        fadeComposable(
            route = "%s/{%s}".format(NavigationItems.Register.route, NavKey.COLOR_DATA.key),
            arguments = listOf(navArg(NavKey.COLOR_DATA)),
        ) {
            RegisterScreen(
                viewModel = hiltViewModel(),
                onBackScreen = ::onBackScreen,
            )
        }
        fadeComposable(
            route = "%s/{%s}".format(NavigationItems.FullColor.route, NavKey.COLOR_DATA.key),
            arguments = listOf(navArg(NavKey.COLOR_DATA)),
        ) {
            it.arguments?.getString(NavKey.COLOR_DATA.key)?.let { colorDataStr ->
                FullColorScreen(
                    colorData = Json.decodeFromString(colorDataStr),
                    onClick = ::onBackScreen,
                )
            }
        }
        fadeComposable(
            route = "%s/{%s}/{%s}/{%s}".format(
                NavigationItems.SelectColor.route,
                NavKey.CATEGORY.key,
                NavKey.INDEX.key,
                NavKey.NEED_BACK.key,
            ),
            arguments = listOf(
                navArg(NavKey.CATEGORY),
                navArg(NavKey.INDEX),
                navArg(NavKey.NEED_BACK),
            ),
        ) {
            SelectColorScreen(
                viewModel = hiltViewModel(),
                backScreenWithSelectHex = ::backScreenWithSelectHex,
                onBackScreen = ::onBackScreen,
                goToColorDetail = ::goToColorDetail,
                goToCategoryDetail = ::goToCategoryDetail,
            )
        }
        fadeComposable(
            route = "%s/{%s}/{%s}".format(
                NavigationItems.ColorDetail.route,
                NavKey.COLOR_ITEM.key,
                NavKey.CATEGORY_NAME.key,
            ),
            arguments = listOf(
                navArg(navKey = NavKey.COLOR_ITEM),
                navArg(navKey = NavKey.CATEGORY_NAME),
            ),
        ) {
            ColorDetailScreen(
                viewModel = hiltViewModel(),
                onClickDisplayColor = ::onClickDisplayColor,
                onBackScreen = ::onBackScreen,
            )
        }
        fadeComposable(
            route = "%s/{%s}".format(
                NavigationItems.SplitColor.route,
                NavKey.SPLIT_COLOR_PARAM.key,
            ),
            arguments = listOf(navArg(NavKey.SPLIT_COLOR_PARAM)),
        ) {
            it.arguments?.getString(NavKey.SPLIT_COLOR_PARAM.key)?.let { param ->
                SplitColorScreen(
                    splitColorParam = Json.decodeFromString(param),
                    onBackScreen = ::onBackScreen,
                )
            }
        }
        fadeComposable(
            route = "%s/{%s}/{%s}".format(
                NavigationItems.GradationColor.route,
                NavKey.HEX1.key,
                NavKey.HEX2.key,
            ),
            arguments = listOf(navArg(NavKey.HEX1), navArg(NavKey.HEX2)),
        ) {
            GradationColorScreen(
                hex1 = it.arguments?.getString(NavKey.HEX1.key) ?: "FFFFFF",
                hex2 = it.arguments?.getString(NavKey.HEX2.key) ?: "FFFFFF",
                onClick = ::onBackScreen,
            )
        }
        fadeComposable(
            route = "%s/{%s}".format(
                NavigationItems.CategoryDetail.route,
                NavKey.CATEGORY_DETAIL.key,
            ),
            arguments = listOf(navArg(NavKey.CATEGORY_DETAIL)),
        ) {
            CategoryDetailScreen(
                viewModel = hiltViewModel(),
                onBackScreen = ::onBackScreen,
                onBackDataScreen = { navController.navigate(route = NavigationItems.Data.route) },
            )
        }
    }
}
