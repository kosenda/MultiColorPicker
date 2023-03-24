package kosenda.makecolor.view.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kosenda.makecolor.view.FontType

private val LightThemeColors = lightColorScheme()
private val DarkThemeColors = darkColorScheme()

@Composable
fun MakeColorTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isDynamicColor: Boolean = true,
    fontType: FontType = FontType.DEFAULT,
    content: @Composable () -> Unit,
) {
    val dynamicColor = isDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colorScheme = when {
        dynamicColor && isDarkTheme -> {
            dynamicDarkColorScheme(LocalContext.current)
        }
        dynamicColor && !isDarkTheme -> {
            dynamicLightColorScheme(LocalContext.current)
        }
        isDarkTheme -> DarkThemeColors
        else -> LightThemeColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography(fontType = fontType),
        content = content,
    )
}

@Composable
fun MakeColorMaterial2Theme(content: @Composable () -> Unit) {
    androidx.compose.material.MaterialTheme(
        colors = darkColors(
            primary = MaterialTheme.colorScheme.primary,
        ),
        content = content,
    )
}
