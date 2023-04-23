package kosenda.makecolor.core.util

import androidx.compose.ui.graphics.Color
import com.godaddy.android.colorpicker.HsvColor
import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.model.data.CMYK
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.model.data.HEX
import kosenda.makecolor.core.model.data.HSV
import kosenda.makecolor.core.model.data.RGB
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ColorUtilityTest {

    @Test
    fun blackOrWhiteFromRGB_black_thinBlack() {
        assertThat(blackOrWhiteFromRGB(RGB(255f, 255f, 255f))).isEqualTo(ThinBlack)
    }

    @Test
    fun blackOrWhiteFromRGB_white_thinWhite() {
        assertThat(blackOrWhiteFromRGB(RGB(0f, 0f, 0f))).isEqualTo(ThinWhite)
    }

    @Test
    fun hexToHexStrWithSharp_hex_sharp000000() {
        assertThat(hexToHexStrWithSharp(HEX("00", "00", "00")))
            .isEqualTo("#000000")
    }

    @Test
    fun rgbToCmyk_rgb_cmyk() {
        assertThat(rgbToCmyk(RGB(255f, 255f, 255f)))
            .isInstanceOf(CMYK::class.java)
    }

    @Test
    fun cmykToRgb_cmyk_rgb() {
        assertThat(cmykToRgb(CMYK(0f, 0f, 0f, 0f)))
            .isInstanceOf(RGB::class.java)
    }

    @Test
    fun rgbToComplementaryRgb_rgb_notSame() {
        val rgb = RGB(255f, 100f, 100f)
        assertThat(rgbToComplementaryRgb(rgb)).isNotEqualTo(rgb)
    }

    @Test
    fun rgbToOppositeRgb_rgb_notSame() {
        val rgb = RGB(255f, 100f, 100f)
        assertThat(rgbToOppositeRgb(rgb)).isNotEqualTo(rgb)
    }

    @Test
    fun hsvToRGB_hsv_rgb() {
        assertThat(hsvToRGB(HSV(0f, 0f, 0f))).isInstanceOf(RGB::class.java)
    }

    @Test
    fun rgbToHsv_rgb_hsv() {
        assertThat(rgbToHsv(RGB(0f, 0f, 0f))).isInstanceOf(HSV::class.java)
    }

    @Test
    fun hsvColorToHsv_hsvColor_hsv() {
        val hsvColor = HsvColor(0f, 0f, 0f, 0f)
        assertThat(hsvColorToHsv(hsvColor)).isInstanceOf(HSV::class.java)
    }

    @Test
    fun hsvColorToHexStr_hsvColor_hexStrIsLength6() {
        val hsvColor = HsvColor(0f, 0f, 0f, 0f)
        assertThat(hsvColorToHexStr(hsvColor).length).isEqualTo(6)
    }

    @Test
    fun rgbToHex_rgb_hex() {
        assertThat(rgbToHex(RGB(0f, 0f, 0f))).isInstanceOf(HEX::class.java)
    }

    @Test
    fun hexToRGB_hex_rgb() {
        assertThat(hexToRGB("000000")).isInstanceOf(RGB::class.java)
    }

    @Test
    fun colorToRGB_color_rgb() {
        assertThat(colorToRGB(Color.Red)).isInstanceOf(RGB::class.java)
    }

    @Test
    fun hexToColor_hex_color() {
        assertThat(hexToColor("000000")).isInstanceOf(Color::class.java)
    }

    @Test
    fun rgbToColorData_rgb_colorData() {
        assertThat(rgbToColorData(RGB(0f, 0f, 0f)))
            .isInstanceOf(ColorData::class.java)
    }

    @Test
    fun rgbToColor_rgb_color() {
        assertThat(rgbToColor(RGB(0f, 0f, 0f))).isInstanceOf(Color::class.java)
    }
}