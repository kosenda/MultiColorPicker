package kosenda.makecolor.core.ui.state

import android.graphics.Bitmap
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.util.randomColorData
import kosenda.makecolor.core.util.randomHex

data class MergeUiState(
    val colorData: ColorData = randomColorData(),
    val bitmap: Bitmap? = null,
    val selectHex1: String = randomHex(),
    val selectHex2: String = randomHex(),
    val selectCategory1: Category = Category("Category1", 0),
    val selectCategory2: Category = Category("Category1", 0),
    val categories: List<Category> = emptyList(),
    val displayCategory: List<String> = emptyList(),
)
