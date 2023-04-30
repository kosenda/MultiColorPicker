package kosenda.makecolor.core.mock

import kosenda.makecolor.core.model.data.Category

class MockCategory {
    val item = Category(name = "test1", size = 0, alias = "alias", id = 0)

    val list = listOf(
        item,
        Category(name = "test2", size = 0, alias = "", id = 1),
        Category(name = "test3", size = 10, alias = "", id = 2),
    )
}