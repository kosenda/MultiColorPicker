package kosenda.makecolor.core.util

import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.model.data.Category
import org.junit.Test

class DataUtilityTest {

    @Test
    fun categoryGetNameIfNoAlias_existAlias_returnAlias() {
        val category = Category(name = "category",  size = 0, alias = "temp")
        assertThat(category.getNameIfNoAlias()).isEqualTo(category.alias)
    }

    @Test
    fun categoryGetNameIfNoAlias_notExistAlias_returnName() {
        val category = Category(name = "category",  size = 0, alias = "")
        assertThat(category.getNameIfNoAlias()).isEqualTo(category.name)
    }

    @Test
    fun convertDisplayStringListFromCategories_categories_returnStringList() {
        val categories = listOf(
            Category(name = "category1",  size = 0, alias = ""),
            Category(name = "category2",  size = 0, alias = "temp")
        )
        assertThat(convertDisplayStringListFromCategories(categories))
            .isEqualTo(listOf("category1", "temp"))
    }
}