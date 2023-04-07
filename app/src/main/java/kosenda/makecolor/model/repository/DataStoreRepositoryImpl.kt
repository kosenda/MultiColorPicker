package kosenda.makecolor.model.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kosenda.makecolor.core.datastore.PreferencesKey
import kosenda.makecolor.view.FontType
import kosenda.makecolor.view.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : DataStoreRepository {

    override fun selectedThemeNum(): Flow<Int> {
        return dataStore.data
            .catch { exception ->
                Timber.e("DataStore: %s", exception)
                when (exception) {
                    is IOException -> emit(emptyPreferences())
                    else -> Theme.AUTO.num
                }
            }
            .map { preferences ->
                preferences[PreferencesKey.THEME_NUM] ?: Theme.AUTO.num
            }
    }

    override fun selectedFontType(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                Timber.e("DataStore: %s", exception)
                when (exception) {
                    is IOException -> emit(emptyPreferences())
                    else -> FontType.DEFAULT.fontName
                }
            }
            .map { preferences ->
                preferences[PreferencesKey.FONT_TYPE] ?: FontType.DEFAULT.fontName
            }
    }

    override suspend fun updateThemeNum(newThemeNum: Int) {
        dataStore.edit { it[PreferencesKey.THEME_NUM] = newThemeNum }
    }

    override suspend fun updateFontType(newFontType: FontType) {
        dataStore.edit { it[PreferencesKey.FONT_TYPE] = newFontType.fontName }
    }
}
