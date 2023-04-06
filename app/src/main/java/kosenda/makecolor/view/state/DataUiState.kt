package kosenda.makecolor.view.state

import kosenda.makecolor.core.model.data.Category

data class DataUiState(
    val categories: List<Category> = emptyList(),
)
