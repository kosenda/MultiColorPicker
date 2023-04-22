package kosenda.makecolor.core.util

import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.model.data.RandomType
import org.junit.Test

class RandomColorUtilityTest {
    @Test
    fun outputRandomRGBColors_default_size10() {
        assertThat(outputRandomRGBColors(randomType = RandomType.VIVID).size)
            .isEqualTo(10)
    }

    @Test
    fun outputRandomRGBColors_param1_size1() {
        assertThat(outputRandomRGBColors(randomType = RandomType.VIVID, size = 1).size)
            .isEqualTo(1)
    }

    @Test
    fun randomColorData_output_isColorData() {
        assertThat(randomColorData()).isInstanceOf(ColorData::class.java)
    }
    @Test
    fun randomVividColorData_output_isColorData() {
        assertThat(randomVividColorData()).isInstanceOf(ColorData::class.java)
    }
}