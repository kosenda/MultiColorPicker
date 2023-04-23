package kosenda.makecolor.core.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.model.data.ColorItem
import kosenda.makecolor.core.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ColorRepositoryImplTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    @get:Rule
    val roomRule = RoomRule(context = context)
    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val colorRepositoryImpl = ColorRepositoryImpl(
        colorDao = roomRule.getInstance().colorDao(),
    )

    @Test
    fun getCategory_initial_existData() {
        // 初期値は空でないことを確認
        mainDispatcherRule.testScope.runTest {
            assertThat(colorRepositoryImpl.getCategory()).isNotEmpty()
        }
    }

    @Test
    fun insertCategory_1item_sizeIsChanged() {
        // 追加出来ていることを確認
        mainDispatcherRule.testScope.runTest {
            assertThat(colorRepositoryImpl.getCategory().size).isEqualTo(1)
            colorRepositoryImpl.insertCategory(Category(name = "test", size = 0))
            assertThat(colorRepositoryImpl.getCategory().size).isEqualTo(2)
        }
    }

    @Test
    fun insertColor_3item_sizeIsChanged() {
        // 追加出来てサイズが変わっていることを確認
        mainDispatcherRule.testScope.runTest {
            val category = colorRepositoryImpl.getCategory().first()
            repeat(3) {
                colorRepositoryImpl.insertColor(
                    color = ColorItem(hex = "000000", category = category.name, memo = ""),
                )
            }
            assertThat(colorRepositoryImpl.getColors(category = category.name).size)
                .isEqualTo(3)
            assertThat(colorRepositoryImpl.getSize(category = category.name))
                .isEqualTo(3)
        }
    }

    @Test
    fun isExistCategory_existCategory_true() {
        // カテゴリが存在したときにTrueになることを確認
        mainDispatcherRule.testScope.runTest {
            val category = colorRepositoryImpl.getCategory().first()
            assertThat(colorRepositoryImpl.isExistCategory(category = category.name)).isTrue()
        }
    }

    @Test
    fun isExistCategory_notExistCategory_false() {
        // カテゴリが存在しないときにFalseになることを確認
        mainDispatcherRule.testScope.runTest {
            assertThat(colorRepositoryImpl.isExistCategory(category = "not_exist")).isFalse()
        }
    }

    @Test
    fun updateSize_2item_isChanged() {
        // サイズ変更ができることを確認
        mainDispatcherRule.testScope.runTest {
            val defaultCategory = colorRepositoryImpl.getCategory().first()
            assertThat(colorRepositoryImpl.getCategory().first().size).isEqualTo(0)
            colorRepositoryImpl.updateSize(size = 2, name = defaultCategory.name)
            assertThat(colorRepositoryImpl.getCategory().first().size).isEqualTo(2)
        }
    }

    @Test
    fun colorChangeCategory_newCategoryName_isChanged() {
        // カテゴリ変更ができることを確認
        mainDispatcherRule.testScope.runTest {
            val defaultCategory = colorRepositoryImpl.getCategory().first()
            colorRepositoryImpl.updateCategory(oldName = defaultCategory.name, newName = "new")
            assertThat(colorRepositoryImpl.isExistCategory(defaultCategory.name)).isFalse()
            assertThat(colorRepositoryImpl.getCategory().first().name).isEqualTo("new")
        }
    }

    @Test
    fun decreaseSize_once_isMinus1() {
        // サイズが1減ることを確認
        mainDispatcherRule.testScope.runTest {
            val defaultCategory = colorRepositoryImpl.getCategory().first()
            colorRepositoryImpl.updateSize(size = 3, name = defaultCategory.name)
            assertThat(colorRepositoryImpl.getCategory().first().size).isEqualTo(3)
            colorRepositoryImpl.decreaseSize(name = defaultCategory.name)
            assertThat(colorRepositoryImpl.getCategory().first().size).isEqualTo(2)
        }
    }

    @Test
    fun deleteColor_1item_isDeleted() {
        // 削除出来ていることを確認
        mainDispatcherRule.testScope.runTest {
            val defaultCategory = colorRepositoryImpl.getCategory().first()
            val color = ColorItem(
                hex = "000000",
                category = defaultCategory.name,
                memo = "",
                id = 1,
            )
            colorRepositoryImpl.insertColor(color = color)
            assertThat(colorRepositoryImpl.getSize(category = defaultCategory.name))
                .isEqualTo(1)
            colorRepositoryImpl.deleteColor(colorItem = color)
            assertThat(colorRepositoryImpl.getSize(category = defaultCategory.name))
                .isEqualTo(0)
        }
    }

    @Test
    fun deleteCategory_1item_isDeleted() {
        // 削除出来ていることを確認
        mainDispatcherRule.testScope.runTest {
            val testCategory = "test"
            colorRepositoryImpl.insertCategory(Category(name = testCategory, size = 0))
            assertThat(colorRepositoryImpl.isExistCategory(category = testCategory)).isTrue()
            colorRepositoryImpl.deleteCategory(name = testCategory)
            assertThat(colorRepositoryImpl.isExistCategory(category = testCategory)).isFalse()
        }
    }

    @Test
    fun deleteColors_1item_isDeleted() {
        // 全て削除出来ていることを確認
        mainDispatcherRule.testScope.runTest {
            val defaultCategory = colorRepositoryImpl.getCategory().first()
            repeat(10) {
                colorRepositoryImpl.insertColor(
                    color = ColorItem(hex = "000000", category = defaultCategory.name, memo = ""),
                )
            }
            assertThat(colorRepositoryImpl.getSize(category = defaultCategory.name))
                .isEqualTo(10)
            colorRepositoryImpl.deleteColors(category = defaultCategory.name)
            assertThat(colorRepositoryImpl.getSize(category = defaultCategory.name))
                .isEqualTo(0)
        }
    }

    @Test
    fun deleteAllCategories_once_isDeleted() {
        // 全て削除出来ていることを確認
        mainDispatcherRule.testScope.runTest {
            assertThat(colorRepositoryImpl.getCategory().size).isEqualTo(1)
            colorRepositoryImpl.insertCategory(category = Category(name = "test1", size = 0))
            colorRepositoryImpl.insertCategory(category = Category(name = "test2", size = 0))
            assertThat(colorRepositoryImpl.getCategory().size).isEqualTo(3)
            colorRepositoryImpl.deleteAllCategories()
            assertThat(colorRepositoryImpl.getCategory().size).isEqualTo(1)
        }
    }

    @Test
    fun deleteAllColor_once_isDeleted() {
        // 全て削除出来ていることを確認
        mainDispatcherRule.testScope.runTest {
            val defaultCategory = colorRepositoryImpl.getCategory().first()
            repeat(10) {
                colorRepositoryImpl.insertColor(
                    color = ColorItem(hex = "000000", category = defaultCategory.name, memo = ""),
                )
            }
            assertThat(colorRepositoryImpl.getSize(category = defaultCategory.name))
                .isEqualTo(10)
            colorRepositoryImpl.deleteAllColors()
            assertThat(colorRepositoryImpl.getSize(category = defaultCategory.name))
                .isEqualTo(0)
        }
    }
}