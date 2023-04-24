package kosenda.makecolor.core.ui.data

import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument

enum class NavKey(val key: String, val builder: NavArgumentBuilder.() -> Unit) {
    COLOR_DATA("colorData", { NavType.StringType }),
    CATEGORY("category", { NavType.StringType }),
    INDEX("index", { defaultValue = ColorIndex.FIRST.num }),
    NEED_BACK("needBack", { defaultValue = true }),
    COLOR_ITEM("colorItem", { NavType.StringType }),
    CATEGORY_NAME("categoryName", { NavType.StringType }),
    SPLIT_COLOR_PARAM("splitColorParam", { NavType.StringType }),
    HEX1("hex1", { NavType.StringType }),
    HEX2("hex2", { NavType.StringType }),
    CATEGORY_DETAIL("categoryDetail",  { NavType.StringType }),
}

fun navArg(navKey: NavKey) = navArgument(name = navKey.key, builder = navKey.builder)