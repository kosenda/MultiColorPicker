package kosenda.makecolor.core.data.repository

import kosenda.makecolor.core.model.data.FontType
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun selectedThemeNum(): Flow<Int>
    fun selectedFontType(): Flow<String>
    suspend fun updateThemeNum(newThemeNum: Int)
    suspend fun updateFontType(newFontType: FontType)
}
