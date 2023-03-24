package kosenda.makecolor.view.state

import kosenda.makecolor.model.Category
import kosenda.makecolor.model.util.randomHex
import kosenda.makecolor.view.code.SplitColorNum

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
