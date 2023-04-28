package kosenda.makecolor.core.domain

import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.model.data.CMYKColor
import kosenda.makecolor.core.model.data.HEXColor
import kosenda.makecolor.core.model.data.RGBColor
import org.junit.Test

class InputColorTextUseCaseTest {

    val useCase = InputColorTextUseCase()

    @Test
    fun inputColorTextUseCase_emptyText_empty() {
        // テキストが空の時はEMPTYを返すことを確認
        assertThat(useCase(RGBColor.RED, "")).isEqualTo(InputColorTextState.EMPTY)
    }

    @Test
    fun inputColorTextUseCase_rangeInMaxValue_ok() {
        // テキストがMAXの値を超えていない時はOKを返すことを確認
        assertThat(useCase(RGBColor.RED, (RGBColor.RED.maxValue - 10).toString()))
            .isEqualTo(InputColorTextState.OK)
    }

    @Test
    fun inputColorTextUseCase_angeOutMaxValue_invalid() {
        // テキストがMAXの値を超えている時はINVALIDを返すことを確認
        assertThat(useCase(RGBColor.RED, (RGBColor.RED.maxValue + 10).toString()))
            .isInstanceOf(InputColorTextState.INVALID::class.java)
    }

    @Test
    fun inputColorTextUseCase_hex_ok() {
        // テキストがHEX(6桁)の時はOKを返すことを確認
        assertThat(useCase(HEXColor.HEX, "FFFFFF")).isEqualTo(InputColorTextState.OK)
    }

    @Test
    fun inputColorTextUseCase_hexButNotLength6_invalid() {
        // テキストがHEXではあるが6桁でない時はINVALIDを返すことを確認
        assertThat(useCase(HEXColor.HEX, "F"))
            .isInstanceOf(InputColorTextState.INVALID::class.java)
    }

    @Test
    fun inputColorTextUseCase_unDefinedType_invalid() {
        // 定義されていないタイプの時はINVALIDを返すことを確認
        assertThat(useCase("unDefinedType", "0"))
            .isInstanceOf(InputColorTextState.INVALID::class.java)
    }

    @Test
    fun inputColorTextUseCase_param2isNotNum_invalid() {
        // 引数２が数字ではない場合invalidを返すことを確認
        assertThat(useCase(CMYKColor.CYAN, "AAA"))
            .isInstanceOf(InputColorTextState.INVALID::class.java)
    }
}