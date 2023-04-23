package kosenda.makecolor.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.model.data.FontType
import kosenda.makecolor.core.model.data.Theme
import kosenda.makecolor.core.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class DataStoreRepositoryImplTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val dataStoreRepositoryImpl = DataStoreRepositoryImpl(
        dataStore = PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(name = "DataStore") },
        ),
    )

    @Test
    fun dataStoreRepository_initialTheme_isAutoNum() {
        // テーマの初期値はAUTOであることを確認
        mainDispatcherRule.testScope.runTest {
            assertThat(dataStoreRepositoryImpl.selectedThemeNum().first()).isEqualTo(Theme.AUTO.num)
        }
    }

    @Test
    fun dataStoreRepository_updateThemeNum_isChanged() {
        // テーマの更新ができることを確認
        mainDispatcherRule.testScope.runTest {
            dataStoreRepositoryImpl.updateThemeNum(Theme.DAY.num)
            assertThat(dataStoreRepositoryImpl.selectedThemeNum().first())
                .isEqualTo(Theme.DAY.num)
            dataStoreRepositoryImpl.updateThemeNum(Theme.NIGHT.num)
            assertThat(dataStoreRepositoryImpl.selectedThemeNum().first())
                .isEqualTo(Theme.NIGHT.num)
        }
    }

    @Test
    fun dataStoreRepository_initialCustomFont_isCorporateLogoRounded() {
        // フォントの初期値はDefaultであることを確認
        mainDispatcherRule.testScope.runTest {
            assertThat(dataStoreRepositoryImpl.selectedFontType().first())
                .isEqualTo(FontType.DEFAULT.fontName)
        }
    }

    @Test
    fun dataStoreRepository_updateCustomFont_isChanged() {
        // フォントの更新ができることを確認
        mainDispatcherRule.testScope.runTest {
            dataStoreRepositoryImpl.updateFontType(FontType.HACHI_MARU_POP)
            assertThat(dataStoreRepositoryImpl.selectedFontType().first())
                .isEqualTo(FontType.HACHI_MARU_POP.fontName)
            dataStoreRepositoryImpl.updateFontType(FontType.PACIFICO)
            assertThat(dataStoreRepositoryImpl.selectedFontType().first())
                .isEqualTo(FontType.PACIFICO.fontName)
        }
    }
}