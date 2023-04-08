package kosenda.makecolor.core.ui.state

import kosenda.makecolor.core.data.default.webColor
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.model.data.ColorItem

data class CategoryDetailUiState(
    val isShowNewCategoryDialog: Boolean = false,
    val colors: List<ColorItem> = webColor,
    val category: Category = Category(name = "Category", size = 0),
    val isDefault: Boolean = true,
)
