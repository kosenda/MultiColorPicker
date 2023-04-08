package kosenda.makecolor.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.core.data.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    val themeNum: Flow<Int> = dataStoreRepository.selectedThemeNum()
    val customFont: Flow<String> = dataStoreRepository.selectedFontType()
}
