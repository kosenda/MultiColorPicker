package kosenda.makecolor.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ColorDao {

    // ■ Create
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertColor(color: ColorItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(category: Category)

    // ■ Read
    @Query("select * from colors where category = :category")
    fun getColors(category: String): List<ColorItem>

    @Query("select count(*) from colors where category = :category")
    fun getSize(category: String): Int

    @Query("select * from categories limit 1")
    fun getFirstCategory(): Category

    @Query("select * from categories")
    fun getCategories(): List<Category>

    @Query("select * from categories where name = :name")
    fun getCategory(name: String): Category

    /** 0 -> 存在しない, 1 -> 存在する */
    @Query("select count(*) from categories where name = :name")
    fun isExistCategory(name: String): Int

    // ■ Update
    @Query("update colors set category = :newCategory where category = :category")
    fun colorChangeCategory(category: String, newCategory: String)

    @Query("update categories set size = :size where name = :name")
    fun updateSize(size: Int, name: String)

    @Query("update categories set name = :newName where name = :oldName")
    fun updateCategory(oldName: String, newName: String)

    @Query("update categories set size = size - 1 where name = :name")
    fun decreaseSize(name: String)

    // ■ Delete
    @Query("Delete from colors")
    fun deleteAllColors()

    @Query("Delete from colors where id = :id")
    fun deleteColor(id: Int)

    @Query("Delete from colors where category = :category")
    fun deleteColors(category: String)

    @Query("Delete from categories where name = :name")
    fun deleteCategory(name: String)

    @Query("Delete from categories")
    fun deleteAllCategories()
}
