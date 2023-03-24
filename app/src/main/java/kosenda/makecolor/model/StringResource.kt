package kosenda.makecolor.model

import android.content.Context
import androidx.annotation.StringRes

/**
 * @see → 参考 ViewModelでString resourcesを扱いたい（Mori Atsushi） https://at-sushi.work/blog/23/
 */
data class StringResource(
    @StringRes private val resId: Int,
    private val strings: List<String> = emptyList(),
) {
    companion object {
        fun create(@StringRes resId: Int, vararg strings: String): StringResource {
            return StringResource(resId, listOf(*strings))
        }
    }

    fun getString(context: Context): String = when {
        strings.isEmpty() -> context.getString(resId)
        else -> context.getString(resId, *strings.toTypedArray())
    }
}
