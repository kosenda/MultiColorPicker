package kosenda.makecolor.model

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKey {
    val THEME_NUM = intPreferencesKey("theme_num")
    val FONT_TYPE = stringPreferencesKey("font_type")
}
