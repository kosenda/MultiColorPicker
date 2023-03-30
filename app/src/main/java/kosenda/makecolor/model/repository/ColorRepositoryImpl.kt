package kosenda.makecolor.model.repository

import kosenda.makecolor.model.Category
import kosenda.makecolor.model.ColorDao
import kosenda.makecolor.model.data.ColorItem
import javax.inject.Inject

class ColorRepositoryImpl @Inject constructor(
    private val colorDao: ColorDao,
) : ColorRepository {
    // ■ Create
    override suspend fun insertCategory(category: Category) {
        if (isExistCategory(category.name).not()) {
            colorDao.insertCategory(category = category)
        }
    }
    override suspend fun insertColor(color: ColorItem) = colorDao.insertColor(color = color)

    // ■ Read
    override suspend fun getCategory(): List<Category> {
        val fetchCategories = colorDao.getCategories()
        return when {
            fetchCategories.isEmpty() -> {
                val firstCategory = Category(name = "Category 1", size = 0)
                colorDao.insertCategory(category = firstCategory)
                listOf(firstCategory)
            }
            else -> fetchCategories
        }
    }
    override suspend fun getColors(category: String): List<ColorItem> = colorDao.getColors(category = category)
    override suspend fun getSize(category: String): Int = colorDao.getSize(category = category)
    override suspend fun isExistCategory(category: String): Boolean {
        val size = colorDao.isExistCategory(name = category)
        return size != 0
    }

    // ■ Update
    override suspend fun updateSize(size: Int, name: String) = colorDao.updateSize(size, name)
    override suspend fun updateCategory(oldName: String, newName: String) =
        colorDao.updateCategory(oldName, newName)
    override suspend fun colorChangeCategory(category: String, newCategory: String) =
        colorDao.colorChangeCategory(category, newCategory)
    override suspend fun decreaseSize(name: String) =
        colorDao.decreaseSize(name = name)

    // ■ Delete
    override suspend fun deleteColor(colorItem: ColorItem) {
        colorDao.deleteColor(id = colorItem.id)
        colorDao.decreaseSize(name = colorItem.category)
    }
    override suspend fun deleteCategory(name: String) = colorDao.deleteCategory(name = name)
    override suspend fun deleteColors(category: String) = colorDao.deleteColors(category = category)
    override suspend fun deleteAllCategories() = colorDao.deleteAllCategories()
    override suspend fun deleteAllColors() = colorDao.deleteAllColors()
}
