package kosenda.makecolor.core.mock

import kosenda.makecolor.core.data.repository.ColorRepository
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.model.data.ColorItem
import kosenda.makecolor.core.util.convertDisplayStringListFromCategories

class FakeColorRepository : ColorRepository {

    private val colors = mutableListOf<ColorItem>()
    private val categories = MockCategory().list.toMutableList()

    override suspend fun getColors(category: String): List<ColorItem> {
        return colors.filter { it.category == category }
    }
    override suspend fun getSize(category: String): Int = colors.size
    override suspend fun isExistCategory(category: String): Boolean {
        if (categories.isEmpty()) return false
        convertDisplayStringListFromCategories(categories).forEach {
            if (it == category) return true
        }
        return false
    }
    override suspend fun updateSize(size: Int, name: String) {
        categories.forEach {
            if (it.name == name) it.size = size
        }
    }
    override suspend fun updateCategory(oldName: String, newName: String) {
        categories.forEach {
            if (it.name == oldName) it.name = newName
        }
    }
    override suspend fun colorChangeCategory(category: String, newCategory: String) {}
    override suspend fun deleteColor(colorItem: ColorItem) {
        colors.remove(colorItem)
    }
    override suspend fun decreaseSize(name: String) {}
    override suspend fun deleteCategory(name: String) {
        categories.removeIf { it.name == name }
    }
    override suspend fun deleteColors(category: String) {}
    override suspend fun deleteAllCategories() {}
    override suspend fun deleteAllColors() {}
    override suspend fun insertColor(color: ColorItem) {
        colors.add(color)
    }
    override suspend fun getCategory(): List<Category> {
        return categories
    }
    override suspend fun insertCategory(category: Category) {
        categories.add(category)
    }
}