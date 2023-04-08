package kosenda.makecolor.core.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.palette.graphics.Palette
import kosenda.makecolor.core.util.hexToColor
import kosenda.makecolor.core.ui.state.PaletteColorsState
import javax.inject.Inject

class UpdatePaletteColorsUseCase @Inject constructor() {
    private val defaultIntColor = Color.Transparent.toArgb()
    private fun Int.toColor(): Color {
        return when (this) {
            defaultIntColor -> Color.Transparent
            else -> hexToColor(String.format("%06X", 0xFFFFFF and this))
        }
    }

    operator fun invoke(state: PaletteColorsState, palette: Palette): PaletteColorsState {
        palette.run {
            return state.copy(
                lightVibrantColor = getLightVibrantColor(defaultIntColor).toColor(),
                vibrantColor = getVibrantColor(defaultIntColor).toColor(),
                darkVibrantColor = getDarkVibrantColor(defaultIntColor).toColor(),
                lightMutedColor = getLightMutedColor(defaultIntColor).toColor(),
                mutedColor = getMutedColor(defaultIntColor).toColor(),
                darkMutedColor = getDarkMutedColor(defaultIntColor).toColor(),
            )
        }
    }

    operator fun invoke(state: PaletteColorsState): PaletteColorsState {
        return state.copy(
            lightVibrantColor = Color.Transparent,
            vibrantColor = Color.Transparent,
            darkVibrantColor = Color.Transparent,
            lightMutedColor = Color.Transparent,
            mutedColor = Color.Transparent,
            darkMutedColor = Color.Transparent,
        )
    }
}
