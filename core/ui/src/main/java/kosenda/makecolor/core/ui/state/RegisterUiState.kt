package kosenda.makecolor.core.ui.state

import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.util.randomColorData

data class RegisterUiState(
    val colorData: ColorData = randomColorData(),
    val memo: String = "",
    val categories: List<Category> = emptyList(),
    val selectCategory: Category = Category("Category1", 0),
    val displayCategories: List<String> = emptyList(),
    val isShowNewCategoryDialog: Boolean = false,
    val showToast: Boolean = false,
)
