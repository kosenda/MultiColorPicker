package kosenda.makecolor.core.model.data

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDetailParam(
    val colors: List<ColorItem>,
    val category: Category,
    val isDefault: Boolean,
)
