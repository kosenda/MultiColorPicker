package kosenda.makecolor.core.domain

import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.spyk
import kosenda.makecolor.core.mock.FakeColorRepository
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchCategoriesUseCaseTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val colorRepository = spyk(FakeColorRepository())
    private val fetchCategoriesUseCase = FetchCategoriesUseCase(
        colorRepository = colorRepository,
    )

    @Test
    fun fetchCategoriesUseCase_emptyList_onlyFetchCategory() = runTest {
        // デフォルト値がないときにrepositoryからカテゴリーが取得できることを確認
        val colorRepositoryCategorySize = colorRepository.getCategory().size
        val actualSize = fetchCategoriesUseCase(defaultCategories = listOf()).size
        assertThat(actualSize).isEqualTo(colorRepositoryCategorySize)
        coVerify { colorRepository.getCategory() }
    }

    @Test
    fun fetchCategoriesUseCase_defaultList_notOnlyFetchCategory() = runTest {
        // デフォルト値がある時にデフォルト値とrepositoryからカテゴリーが取得できることを確認
        val colorRepositoryCategorySize = colorRepository.getCategory().size
        val defaultCategories = listOf(
            Category(name = "test1", size = 0),
            Category(name = "test2", size = 0),
        )
        val actualSize = fetchCategoriesUseCase(defaultCategories = defaultCategories).size
        assertThat(actualSize)
            .isEqualTo(colorRepositoryCategorySize + defaultCategories.size)
        coVerify { colorRepository.getCategory() }
    }
}