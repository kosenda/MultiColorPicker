package kosenda.makecolor.core.domain

import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.model.data.CMYK
import kosenda.makecolor.core.model.data.ColorType
import kosenda.makecolor.core.model.data.HSV
import kosenda.makecolor.core.model.data.RGB
import kosenda.makecolor.core.util.randomColorData
import org.junit.Test

class UpdateOtherColorUseCaseTest {

    val useCase = UpdateOtherColorUseCase()

    @Test
    fun updateOtherColorUseCase_rgb_changeOtherColorData() {
        // RGBを変更した時、他の色データも変更されていることを確認
        val randomColorData = randomColorData()
        var changedRgbColorData = randomColorData.copy(rgb = RGB(10f, 20f, 60f))
        if (randomColorData == changedRgbColorData) {
            changedRgbColorData = randomColorData.copy(rgb = RGB(60f, 20f, 10f))
        }
        val changedOtherColorData = useCase(changedRgbColorData, ColorType.RGB)
        assertThat(changedOtherColorData).isNotEqualTo(randomColorData)
        assertThat(changedOtherColorData).isNotEqualTo(changedRgbColorData)
    }

    @Test
    fun updateOtherColorUseCase_cmyk_changeOtherColorData() {
        // CMYKを変更した時、他の色データも変更されていることを確認
        val randomColorData = randomColorData()
        var changedRgbColorData = randomColorData
            .copy(cmyk = CMYK(10f, 20f, 60f, 10f))
        if (randomColorData == changedRgbColorData) {
            changedRgbColorData = randomColorData
                .copy(cmyk = CMYK(60f, 20f, 10f, 10f))
        }
        val changedOtherColorData = useCase(changedRgbColorData, ColorType.CMYK)
        assertThat(changedOtherColorData).isNotEqualTo(randomColorData)
        assertThat(changedOtherColorData).isNotEqualTo(changedRgbColorData)
    }

    @Test
    fun updateOtherColorUseCase_hsv_changeOtherColorData() {
        // RGBを変更した時、他の色データも変更されていることを確認
        val randomColorData = randomColorData()
        var changedRgbColorData = randomColorData
            .copy(hsv = HSV(10f, 20f, 60f))
        if (randomColorData == changedRgbColorData) {
            changedRgbColorData = randomColorData
                .copy(hsv = HSV(60f, 20f, 10f))
        }
        val changedOtherColorData = useCase(changedRgbColorData, ColorType.HSV)
        assertThat(changedOtherColorData).isNotEqualTo(randomColorData)
        assertThat(changedOtherColorData).isNotEqualTo(changedRgbColorData)
    }
}