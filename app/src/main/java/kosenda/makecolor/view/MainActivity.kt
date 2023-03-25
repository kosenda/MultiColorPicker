package kosenda.makecolor.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import kosenda.makecolor.view.screen.FirstScreen
import kosenda.makecolor.view.theme.MakeColorTheme
import kosenda.makecolor.viewmodel.MainViewModel

/**
 * 作成開始：2021/06/13
 * 色を作成するアプリ
*/
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val themeNum: State<Int> =
                mainViewModel.themeNum.collectAsState(initial = Theme.AUTO.num)
            val fontType: State<String> =
                mainViewModel.customFont.collectAsState(initial = FontType.DEFAULT.name)

            val isDarkTheme = when (themeNum.value) {
                Theme.NIGHT.num -> true
                Theme.DAY.num -> false
                else -> isSystemInDarkTheme()
            }

            MakeColorTheme(
                isDarkTheme = isDarkTheme,
                fontType = when (fontType.value) {
                    FontType.DEFAULT.fontName -> FontType.DEFAULT
                    FontType.ROCKN_ROLL_ONE.fontName -> FontType.ROCKN_ROLL_ONE
                    FontType.ROBOTO_SLAB.fontName -> FontType.ROBOTO_SLAB
                    FontType.PACIFICO.fontName -> FontType.PACIFICO
                    FontType.HACHI_MARU_POP.fontName -> FontType.HACHI_MARU_POP
                    else -> throw IllegalArgumentException("定義されていないフォント: $fontType")
                },
            ) {
                CompositionLocalProvider(LocalIsDark provides isDarkTheme) {
                    FirstScreen()
                }
            }
        }
    }
}
