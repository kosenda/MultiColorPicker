package kosenda.makecolor.core.domain

import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.model.data.CMYKColor
import kosenda.makecolor.core.model.data.HSVColor
import kosenda.makecolor.core.model.data.RGBColor
import kosenda.makecolor.core.util.randomColorData
import org.junit.Test

class UpdateColorDataUseCaseTest {

    private val updateOtherColorUseCase = UpdateOtherColorUseCase()
    private val colorData = randomColorData()
    val useCase = UpdateColorDataUseCase(updateOtherColorUseCase = updateOtherColorUseCase)


    @Test
    fun updateColorDataUseCase_red_updateColorData() {
        // 赤を変更した時、他の色データも変更されていることを確認
        val updateValue = if (colorData.rgb.red > 50f) 25f else 75f
        val changedColorData = useCase(colorData, RGBColor.RED, updateValue)
        assertThat(changedColorData).isNotEqualTo(colorData)
    }

    @Test
    fun updateColorDataUseCase_cyan_updateColorData() {
        // シアンを変更した時、他の色データも変更されていることを確認
        val updateValue = if (colorData.cmyk.cyan > 50f) 10f else 90f
        val changedColorData = useCase(colorData, CMYKColor.CYAN, updateValue)
        assertThat(changedColorData).isNotEqualTo(colorData)
    }

    @Test
    fun updateColorDataUseCase_hue_updateColorData() {
        // Hueを変更した時、他の色データも変更されていることを確認
        val updateValue = if (colorData.hsv.hue > 180f) 30f else 330f
        val changedColorData = useCase(colorData, HSVColor.HUE, updateValue)
        assertThat(changedColorData).isNotEqualTo(colorData)
    }
}