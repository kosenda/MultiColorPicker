package kosenda.makecolor.core.data.repository

import kosenda.makecolor.core.model.data.FontType
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun selectedThemeNum(): Flow<Int>
    fun selectedFontType(): Flow<String>
    fun fetchCountForReview(): Flow<Int>
    suspend fun updateThemeNum(newThemeNum: Int)
    suspend fun updateFontType(newFontType: FontType)
    suspend fun updateCountForReview(newCount: Int)
}
