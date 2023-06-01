package kosenda.makecolor

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.crashlytics.ktx.BuildConfig
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kosenda.makecolor.core.model.data.FontType
import kosenda.makecolor.core.model.data.Theme
import kosenda.makecolor.core.ui.feature.common.LocalIsDark
import kosenda.makecolor.core.ui.feature.common.dialog.RequestReviewDialog
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import timber.log.Timber

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

        Timber.plant(tree = if (BuildConfig.DEBUG) Timber.DebugTree() else ReleaseTree())

        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val themeNum: State<Int> =
                mainViewModel.themeNum.collectAsState(initial = Theme.AUTO.num)
            val fontType: State<String> =
                mainViewModel.customFont.collectAsState(initial = FontType.DEFAULT.fontName)

            val isDarkTheme = when (themeNum.value) {
                Theme.NIGHT.num -> true
                Theme.DAY.num -> false
                else -> isSystemInDarkTheme()
            }

            LaunchedEffect(Unit) {
                mainViewModel.checkCountForReview(
                    onReachMaxCount = mainViewModel::showReviewDialog,
                )
            }

            if (mainViewModel.isShowRequestReviewDialog) {
                RequestReviewDialog(
                    onCancel = {
                        mainViewModel.closeReviewDialog()
                        mainViewModel.updateCountForReview(isCompletedReview = false)
                    },
                    onClick = {
                        mainViewModel.closeReviewDialog()
                        val reviewManager = ReviewManager(
                            context = this,
                            onComplete = {
                                mainViewModel.updateCountForReview(isCompletedReview = true)
                            },
                            onCancel = {
                                mainViewModel.updateCountForReview(isCompletedReview = false)
                            },
                        )
                        reviewManager.startReviewFlow()
                    },
                )
            }

            MakeColorTheme(
                isDarkTheme = isDarkTheme,
                fontType = when (fontType.value) {
                    FontType.DEFAULT.fontName -> FontType.DEFAULT
                    FontType.ROCKN_ROLL_ONE.fontName -> FontType.ROCKN_ROLL_ONE
                    FontType.ROBOTO_SLAB.fontName -> FontType.ROBOTO_SLAB
                    FontType.PACIFICO.fontName -> FontType.PACIFICO
                    FontType.HACHI_MARU_POP.fontName -> FontType.HACHI_MARU_POP
                    else -> throw IllegalArgumentException("undefined: ${fontType.value}")
                },
            ) {
                CompositionLocalProvider(LocalIsDark provides isDarkTheme) {
                    FirstScreen()
                }
            }
        }
    }
}

private class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val priorityStr = when (priority) {
            Log.ERROR -> "E/"
            Log.WARN -> "W/"
            Log.DEBUG -> "D/"
            else -> return
        }
        Firebase.crashlytics.log(
            "%s tag: %s, message: %s, t:%s".format(
                priorityStr,
                tag ?: "",
                message,
                t ?: "",
            ),
        )
    }
}
