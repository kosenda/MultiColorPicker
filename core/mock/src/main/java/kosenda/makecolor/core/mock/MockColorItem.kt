package kosenda.makecolor.core.mock

import kosenda.makecolor.core.model.data.ColorItem

class MockColorItem {
    val item = ColorItem(hex = "#FFFFFF", category = "test1", memo = "test1", id = 0)

    val list = listOf(
        item,
        ColorItem(hex = "#115599", category = "test2", memo = "test2", id = 1),
        ColorItem(hex = "#AA00AA", category = "test3", memo = "test3", id = 2),
    )
}