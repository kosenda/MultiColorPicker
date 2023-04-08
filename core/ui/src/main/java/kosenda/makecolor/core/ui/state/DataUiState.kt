package kosenda.makecolor.core.ui.state

import kosenda.makecolor.core.model.data.Category

data class DataUiState(
    val categories: List<Category> = emptyList(),
)
