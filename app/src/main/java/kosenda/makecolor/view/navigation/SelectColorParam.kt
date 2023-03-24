package kosenda.makecolor.view.navigation

import kosenda.makecolor.model.Category
import kosenda.makecolor.view.code.ColorIndex

data class SelectColorParam(
    val category: Category,
    val index: ColorIndex,
    val needBack: Boolean = true,
)
