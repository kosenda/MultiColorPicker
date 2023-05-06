package kosenda.makecolor.core.mock

import kosenda.makecolor.core.data.default.DefaultCategory
import kosenda.makecolor.core.data.default.copicColor
import kosenda.makecolor.core.data.default.jisIdiomaticColor
import kosenda.makecolor.core.data.default.jisSafetyColor
import kosenda.makecolor.core.data.default.webColor
import kosenda.makecolor.core.data.default.x11Color
import kosenda.makecolor.core.model.data.Category

class MockDefaultCategories  {
    val x11 = Category(
        name = DefaultCategory.X11.name,
        alias = "X11 color names",
        size = x11Color.size,
    )
    val web = Category(
        name = DefaultCategory.WEB.name,
        alias = "Web colors",
        size = webColor.size,
    )
    val copic = Category(
        name = DefaultCategory.COPIC.name,
        alias = "Copic Color",
        size = copicColor.size,
    )
    val jisIdiomatic = Category(
        name = DefaultCategory.JIS_IDIOMATIC.name,
        alias = "JIS conventional color name",
        size = jisIdiomaticColor.size,
    )
    val jisSafety = Category(
        name = DefaultCategory.JIS_SAFETY.name,
        alias = "JIS safe color",
        size = jisSafetyColor.size,
    )
}