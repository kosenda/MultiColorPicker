package kosenda.makecolor.model.usecase

import kosenda.makecolor.model.Category
import kosenda.makecolor.model.repository.ColorRepository
import javax.inject.Inject

class FetchCategoriesUseCase @Inject constructor(
    private val colorRepository: ColorRepository,
) {
    suspend operator fun invoke(defaultCategories: List<Category>): List<Category> {
        val fetchCategory = colorRepository.getCategory()
        return mutableListOf<Category>().apply {
            defaultCategories.map { add(it) }
            fetchCategory.map { add(it) }
        }
    }
}
