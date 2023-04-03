package kosenda.makecolor.model.default

import android.content.Context
import kosenda.makecolor.R
import kosenda.makecolor.model.Category

enum class DefaultCategory {
    WEB,
    X11,
    COPIC,
    JIS_IDIOMATIC,
    JIS_SAFETY,
}

fun defaultCategories(context: Context): List<Category> {
    return listOf(
        Category(
            name = DefaultCategory.WEB.name,
            alias = context.getString(R.string.web_color),
            size = webColor.size,
        ),
        Category(
            name = DefaultCategory.X11.name,
            alias = context.getString(R.string.x11_color),
            size = x11Color.size,
        ),
        Category(
            name = DefaultCategory.COPIC.name,
            alias = context.getString(R.string.copic_color),
            size = copicColor.size,
        ),
        Category(
            name = DefaultCategory.JIS_IDIOMATIC.name,
            alias = context.getString(R.string.jis_idiomatic_color),
            size = jisIdiomaticColor.size,
        ),
        Category(
            name = DefaultCategory.JIS_SAFETY.name,
            alias = context.getString(R.string.jis_safe_color),
            size = jisSafetyColor.size,
        ),
    )
}
