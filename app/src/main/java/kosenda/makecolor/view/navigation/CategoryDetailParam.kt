package kosenda.makecolor.view.navigation

import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.model.data.ColorItem
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDetailParam(
    val colors: List<ColorItem>,
    val category: Category,
    val isDefault: Boolean,
)
