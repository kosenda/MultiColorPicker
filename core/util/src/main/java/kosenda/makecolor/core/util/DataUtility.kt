package kosenda.makecolor.core.util

import kosenda.makecolor.core.model.data.Category

fun Category.getNameIfNoAlias(): String {
    return when {
        this.alias.isEmpty() -> this.name
        else -> this.alias
    }
}

fun convertDisplayStringListFromCategories(categories: List<Category>): List<String> {
    return mutableListOf<String>().apply {
        categories.map { this.add(it.getNameIfNoAlias()) }
    }
}
