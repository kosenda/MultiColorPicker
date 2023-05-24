package kosenda.makecolor.core.data.datastore

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKey {
    val THEME_NUM = intPreferencesKey("theme_num")
    val FONT_TYPE = stringPreferencesKey("font_type")
    val COUNT_FOR_REVIEW = intPreferencesKey("count_for_review")
}
