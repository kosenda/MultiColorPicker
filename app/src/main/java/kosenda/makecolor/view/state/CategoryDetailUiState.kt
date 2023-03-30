package kosenda.makecolor.view.state

import kosenda.makecolor.model.Category
import kosenda.makecolor.model.data.ColorItem
import kosenda.makecolor.model.default.webColor

data class CategoryDetailUiState(
    val isShowNewCategoryDialog: Boolean = false,
    val colors: List<ColorItem> = webColor,
    val category: Category = Category(name = "Category", size = 0),
    val isDefault: Boolean = true,
)
