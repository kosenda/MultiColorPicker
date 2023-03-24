package kosenda.makecolor.view.state

import kosenda.makecolor.model.Category
import kosenda.makecolor.model.ColorData
import kosenda.makecolor.model.util.randomColorData

data class RegisterUiState(
    val colorData: ColorData = randomColorData(),
    val memo: String = "",
    val categories: List<Category> = emptyList(),
    val selectCategory: Category = Category("Category1", 0),
    val displayCategories: List<String> = emptyList(),
    val isShowNewCategoryDialog: Boolean = false,
)
