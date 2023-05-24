package kosenda.makecolor

import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.data.repository.DataStoreRepository
import kosenda.makecolor.core.model.data.FontType
import kosenda.makecolor.core.model.data.Theme
import kosenda.makecolor.core.testing.MainDispatcherRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun mainViewModel_values_canGetValue() = runTest {
        val mainViewModel = MainViewModel(
            dataStoreRepository = FakeDataStoreRepository(),
            ioDispatcher = mainDispatcherRule.testDispatcher,
        )
        assertThat(mainViewModel.themeNum.first()).isEqualTo(Theme.AUTO.num)
        assertThat(mainViewModel.customFont.first()).isEqualTo(FontType.DEFAULT.fontName)
    }
 }

 private class FakeDataStoreRepository : DataStoreRepository {
    override fun selectedThemeNum(): Flow<Int> = flow { emit(Theme.AUTO.num) }
    override fun selectedFontType(): Flow<String> = flow { emit(FontType.DEFAULT.fontName) }
     override fun fetchCountForReview(): Flow<Int> = flow { emit(0) }

     override suspend fun updateThemeNum(newThemeNum: Int) {}
    override suspend fun updateFontType(newFontType: FontType) {}
     override suspend fun updateCountForReview(newCount: Int) {}
 }
