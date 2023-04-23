package kosenda.makecolor.core.model.data

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ColorDataTest {

    @Test
    fun toString_rgb_isFormatted() {
        val rgb = RGB(red = 255f, green = 255f, blue = 255f)
        assertThat(rgb.toString()).isEqualTo("R: 255 G: 255 B: 255")
    }

    @Test
    fun toString_cmyk_isFormatted() {
        val cmyk = CMYK(cyan = 100f, magenta = 100f, yellow = 100f, black = 100f)
        assertThat(cmyk.toString()).isEqualTo("C: 100 M: 100 Y: 100 K: 100")
    }

    @Test
    fun toString_hsv_isFormatted() {
        val hsv = HSV(hue = 100f, saturation = 100f, value = 100f)
        assertThat(hsv.toString()).isEqualTo("H: 100 S: 100 V: 100")
    }

    @Test
    fun toString_hex_isFormatted() {
        val hex = HEX(red = "FF", green = "FF", blue = "FF")
        assertThat(hex.toString()).isEqualTo("FFFFFF")
    }

}