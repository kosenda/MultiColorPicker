package kosenda.makecolor

import com.google.common.truth.Truth.assertThat
import kosenda.makecolor.core.data.repository.DataStoreRepository
import kosenda.makecolor.core.model.data.FontType
import kosenda.makecolor.core.model.data.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    @Test
    fun mainViewModel_values_canGetValue() = runTest {
        val mainViewModel = MainViewModel(dataStoreRepository = FakeDataStoreRepository())
        assertThat(mainViewModel.themeNum.first()).isEqualTo(Theme.AUTO.num)
        assertThat(mainViewModel.customFont.first()).isEqualTo(FontType.DEFAULT.fontName)
    }
}

private class FakeDataStoreRepository : DataStoreRepository {
    override fun selectedThemeNum(): Flow<Int> = flow { emit(Theme.AUTO.num) }
    override fun selectedFontType(): Flow<String> = flow { emit(FontType.DEFAULT.fontName) }
    override suspend fun updateThemeNum(newThemeNum: Int) {}
    override suspend fun updateFontType(newFontType: FontType) {}
}