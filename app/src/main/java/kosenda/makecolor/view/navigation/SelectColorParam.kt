package kosenda.makecolor.view.navigation

import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.ui.code.ColorIndex

data class SelectColorParam(
    val category: Category,
    val index: ColorIndex,
    val needBack: Boolean = true,
)
