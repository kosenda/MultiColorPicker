package kosenda.makecolor.model.repository

import kosenda.makecolor.view.FontType
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun selectedThemeNum(): Flow<Int>
    fun selectedFontType(): Flow<String>
    suspend fun updateThemeNum(newThemeNum: Int)
    suspend fun updateFontType(newFontType: FontType)
}
