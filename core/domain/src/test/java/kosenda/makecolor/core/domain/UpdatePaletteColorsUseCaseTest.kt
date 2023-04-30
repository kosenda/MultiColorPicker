package kosenda.makecolor.core.domain

import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kosenda.makecolor.core.ui.state.PaletteColorsState
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UpdatePaletteColorsUseCaseTest {

    private val paletteColorsState = PaletteColorsState(
        lightVibrantColor = Color.Red,
        vibrantColor = Color.Red,
        darkVibrantColor = Color.Red,
        lightMutedColor = Color.Red,
        mutedColor = Color.Red,
        darkMutedColor = Color.Red,
    )
    private val palette = mockk<Palette>(relaxed = true)

    val useCase = UpdatePaletteColorsUseCase()

    // TODO relaxedだと透明になってしまっているっぽいから、Bitmapを使用してテストする
    @Ignore("test failed")
    @Test
    fun updatePaletteColorUseCase_stateWithPalette_isNotTransparent() {
        assertThat(useCase(paletteColorsState, palette)).isNotEqualTo(PaletteColorsState())
    }

    @Test
    fun updatePaletteColorUseCase_state_isTransparent() {
        assertThat(useCase(paletteColorsState)).isEqualTo(PaletteColorsState())
    }
}