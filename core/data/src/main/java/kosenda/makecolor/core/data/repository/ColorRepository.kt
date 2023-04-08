package kosenda.makecolor.core.data.repository

import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.model.data.ColorItem

interface ColorRepository {
    suspend fun insertCategory(category: Category)
    suspend fun insertColor(color: ColorItem)
    suspend fun getCategory(): List<Category>
    suspend fun getColors(category: String): List<ColorItem>
    suspend fun getSize(category: String): Int
    suspend fun isExistCategory(category: String): Boolean
    suspend fun updateSize(size: Int, name: String)
    suspend fun updateCategory(oldName: String, newName: String)
    suspend fun colorChangeCategory(category: String, newCategory: String)
    suspend fun deleteColor(colorItem: ColorItem)
    suspend fun decreaseSize(name: String)
    suspend fun deleteCategory(name: String)
    suspend fun deleteColors(category: String)
    suspend fun deleteAllCategories()
    suspend fun deleteAllColors()
}
