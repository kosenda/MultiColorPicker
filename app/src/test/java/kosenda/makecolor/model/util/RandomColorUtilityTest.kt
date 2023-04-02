package kosenda.makecolor.model.util

import kosenda.makecolor.view.code.RandomType
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class RandomColorUtilityTest {
    @Test
    fun outputRandomRGBColors_default_size10() {
        assertThat(outputRandomRGBColors(randomType = RandomType.Vivid).size)
            .isEqualTo(10)
    }
    @Test
    fun outputRandomRGBColors_param1_size1() {
        assertThat(outputRandomRGBColors(randomType = RandomType.Vivid, size = 1).size)
            .isEqualTo(1)
    }

}