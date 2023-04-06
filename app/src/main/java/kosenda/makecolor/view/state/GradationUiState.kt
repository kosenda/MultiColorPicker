package kosenda.makecolor.view.state

import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.model.util.randomHex

data class GradationUiState(
    val selectHex1: String = randomHex(),
    val selectHex2: String = randomHex(),
    val selectCategory1: Category = Category("Category1", 0),
    val selectCategory2: Category = Category("Category1", 0),
    val categories: List<Category> = emptyList(),
    val displayCategories: List<String> = emptyList(),
)
