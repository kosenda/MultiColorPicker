package kosenda.makecolor.core.util

import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.model.data.RandomType
import org.junit.Test

class RandomColorUtilityTest {
    @Test
    fun outputRandomRGBColors_default_size10() {
        assertThat(outputRandomRGBColors(randomType = RandomType.Vivid).size)
            .isEqualTo(100)
    }

    @Test
    fun outputRandomRGBColors_param1_size1() {
        assertThat(outputRandomRGBColors(randomType = RandomType.Vivid, size = 1).size)
            .isEqualTo(1)
    }
}