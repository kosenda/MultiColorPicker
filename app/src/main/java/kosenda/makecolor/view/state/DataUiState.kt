package kosenda.makecolor.view.state

import kosenda.makecolor.model.Category

data class DataUiState(
    val categories: List<Category> = emptyList(),
)
