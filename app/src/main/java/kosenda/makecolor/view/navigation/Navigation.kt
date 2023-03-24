package kosenda.makecolor.view.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kosenda.makecolor.model.ColorData
import kosenda.makecolor.model.ColorItem
import kosenda.makecolor.view.code.ColorIndex
import kosenda.makecolor.view.screen.CategoryDetailScreen
import kosenda.makecolor.view.screen.ColorDetailScreen
import kosenda.makecolor.view.screen.DataScreen
import kosenda.makecolor.view.screen.FullColorScreen
import kosenda.makecolor.view.screen.GradationColorScreen
import kosenda.makecolor.view.screen.GradationScreen
import kosenda.makecolor.view.screen.InputTextScreen
import kosenda.makecolor.view.screen.MergeScreen
import kosenda.makecolor.view.screen.PickerScreen
import kosenda.makecolor.view.screen.PictureScreen
import kosenda.makecolor.view.screen.RandomScreen
import kosenda.makecolor.view.screen.RegisterScreen
import kosenda.makecolor.view.screen.SeekbarScreen
import kosenda.makecolor.view.screen.SelectColorScreen
import kosenda.makecolor.view.screen.SettingScreen
import kosenda.makecolor.view.screen.SplitColorScreen
import kosenda.makecolor.view.screen.SplitScreen
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    onClickMenu: () -> Unit,
) {
    val animationSpec: FiniteAnimationSpec<Float> = tween(durationMillis = 400)

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
                onClickDisplayColor = ::onClickDisplayColor,
                onClickFloatingButton = ::onClickFloatingButton,
            )
        }
        horizontalComposable(NavigationItems.Seekbar.route) {
            SeekbarScreen(
                viewModel = hiltViewModel(),
                onClickMenu = onClickMenu,
                onClickDisplayColor = ::onClickDisplayColor,
                onClickFloatingButton = ::onClickFloatingButton,
            )
        }
        horizontalComposable(NavigationItems.Text.route) {
            InputTextScreen(
                viewModel = hiltViewModel(),
                onClickMenu = onClickMenu,
                onClickDisplayColor = ::onClickDisplayColor,
                onClickFloatingButton = ::onClickFloatingButton,
            )
        }
        horizontalComposable(NavigationItems.Picture.route) {
            PictureScreen(
                viewModel = hiltViewModel(),
                onClickMenu = onClickMenu,
                onClickDisplayColor = ::onClickDisplayColor,
                onClickFloatingButton = ::onClickFloatingButton,
            )
        }
        horizontalComposable(NavigationItems.Merge.route) {
            MergeScreen(
                navController = navController,
                viewModel = hiltViewModel(),
                onClickMenu = onClickMenu,
                onClickDisplayColor = ::onClickDisplayColor,
                onClickFloatingButton = ::onClickFloatingButton,
                onClickSelectColor = ::onClickSelectColor,
            )
        }
        horizontalComposable(NavigationItems.Random.route) {
            RandomScreen(
                viewModel = hiltViewModel(),
                onClickMenu = onClickMenu,
                onClickDisplayColor = ::onClickDisplayColor,
                onClickFloatingButton = ::onClickFloatingButton,
            )
        }
        fadeComposable(NavigationItems.Data.route) {
            DataScreen(
                viewModel = hiltViewModel(),
                onClickMenu = onClickMenu,
                onClickSelectColor = ::onClickSelectColor,
            )
        }
        horizontalComposable(NavigationItems.Split.route) {
            SplitScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                onClickMenu = onClickMenu,
                onClickSelectColor = ::onClickSelectColor,
            )
        }
        horizontalComposable(NavigationItems.Gradation.route) {
            GradationScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                onClickMenu = onClickMenu,
                onClickDisplayGradationColor = ::onClickDisplayGradationColor,
                onClickSelectColor = ::onClickSelectColor,
            )
        }
        horizontalComposable(NavigationItems.Setting.route) {
            SettingScreen(
                viewModel = hiltViewModel(),
                onClickMenu = onClickMenu,
            )
        }
        fadeComposable(
            route = "${NavigationItems.Register.route}/{colorData}",
            arguments = listOf(navArgument("colorData") { NavType.StringType }),
        ) {
            RegisterScreen(
                viewModel = hiltViewModel(),
                onBackScreen = ::onBackScreen,
            )
        }
        fadeComposable(
            route = "${NavigationItems.FullColor.route}/{colorData}",
            arguments = listOf(navArgument("colorData") { NavType.StringType }),
        ) {
            it.arguments?.getString("colorData")?.let { colorDataStr ->
                FullColorScreen(
                    colorData = Json.decodeFromString(colorDataStr),
                    onClick = ::onBackScreen,
                )
            }
        }
        fadeComposable(
            route = "%s/{%s}/{%s}/{%s}".format(
                NavigationItems.SelectColor.route,
                "category",
                "index",
                "needBack",
            ),
            arguments = listOf(
                navArgument("category") { NavType.StringType },
                navArgument("index") { defaultValue = ColorIndex.FIRST.num },
                navArgument("needBack") { defaultValue = true },
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
            route = "${NavigationItems.ColorDetail.route}/{colorItem}/{categoryName}",
            arguments = listOf(
                navArgument("colorItem") { NavType.StringType },
                navArgument("categoryName") { NavType.StringType },
            ),
        ) {
            ColorDetailScreen(
                viewModel = hiltViewModel(),
                onClickDisplayColor = ::onClickDisplayColor,
                onBackScreen = ::onBackScreen,
            )
        }
        fadeComposable(
            route = "${NavigationItems.SplitColor.route}/{splitColorParam}",
            arguments = listOf(
                navArgument("splitColorParam") { NavType.StringType },
            ),
        ) {
            it.arguments?.getString("splitColorParam")?.let { param ->
                SplitColorScreen(
                    splitColorParam = Json.decodeFromString(param),
                    onBackScreen = ::onBackScreen,
                )
            }
        }
        fadeComposable(
            route = "${NavigationItems.GradationColor.route}/{hex1}/{hex2}",
            arguments = listOf(
                navArgument("hex1") { NavType.StringType },
                navArgument("hex2") { NavType.StringType },
            ),
        ) {
            GradationColorScreen(
                hex1 = it.arguments?.getString("hex1") ?: "FFFFFF",
                hex2 = it.arguments?.getString("hex2") ?: "FFFFFF",
                onClick = ::onBackScreen,
            )
        }
        fadeComposable(
            route = "${NavigationItems.CategoryDetail.route}/{categoryDetail}",
            arguments = listOf(
                navArgument("categoryDetail") { NavType.StringType },
            ),
        ) {
            CategoryDetailScreen(
                viewModel = hiltViewModel(),
                onBackScreen = ::onBackScreen,
                onBackDataScreen = { navController.navigate(route = NavigationItems.Data.route) },
            )
        }
    }
}
