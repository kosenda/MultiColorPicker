package kosenda.makecolor.core.ui.feature.common

import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.ui.data.ColorIndex

data class SelectColorParam(
    val category: Category,
    val index: ColorIndex,
    val needBack: Boolean = true,
)
