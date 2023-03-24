package kosenda.makecolor.view.navigation

import kosenda.makecolor.model.Category
import kosenda.makecolor.model.ColorItem
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDetailParam(
    val colors: List<ColorItem>,
    val category: Category,
    val isDefault: Boolean,
)
