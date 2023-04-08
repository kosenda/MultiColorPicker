package kosenda.makecolor.core.ui.state

import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.ui.code.SplitColorNum
import kosenda.makecolor.core.util.randomHex

data class SplitUiState(
    val selectSplitColorNum: SplitColorNum = SplitColorNum.THREE,
    val selectHex1: String = randomHex(),
    val selectHex2: String = randomHex(),
    val selectHex3: String = randomHex(),
    val selectHex4: String = randomHex(),
    val selectCategory1: Category = Category("Category1", 0),
    val selectCategory2: Category = Category("Category1", 0),
    val selectCategory3: Category = Category("Category1", 0),
    val selectCategory4: Category = Category("Category1", 0),
    val categories: List<Category> = emptyList(),
)
