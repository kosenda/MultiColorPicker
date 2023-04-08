package kosenda.makecolor.core.ui.state

import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.ui.code.ColorIndex
import kosenda.makecolor.core.model.data.ColorItem

data class SelectColorUiState(
    val colors: List<ColorItem> = emptyList(),
    val category: Category = Category(name = "Category1", size = 0),
    val index: Int = ColorIndex.FIRST.num,
    val needBack: Boolean = true,
)
