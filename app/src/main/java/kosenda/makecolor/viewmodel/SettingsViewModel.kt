package kosenda.makecolor.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.di.IODispatcher
import kosenda.makecolor.model.repository.ColorRepository
import kosenda.makecolor.model.repository.DataStoreRepository
import kosenda.makecolor.view.FontType
import kosenda.makecolor.view.Theme
import kosenda.makecolor.view.state.SettingUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

abstract class SettingsViewModel : ViewModel() {
    abstract val uiState: StateFlow<SettingUiState>
    abstract val fontType: MutableState<FontType>
    abstract val themeNum: MutableState<Int>
    abstract fun updateThemeNum(newThemeNum: Int)
    abstract fun updateFontType(newFontType: FontType)
    abstract fun isSelectedThemeNum(themeNum: Int): Boolean
    abstract fun isSelectedFont(fontType: FontType): Boolean
    abstract fun openConfirmDialog()
    abstract fun closeConfirmDialog()
    abstract fun openSelectLanguageDialog()
    abstract fun closeSelectLanguageDialog()
    abstract suspend fun deleteAllData()
}

@HiltViewModel
class SettingsViewModelImpl @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val colorRepository: ColorRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : SettingsViewModel() {
    private val _uiState = MutableStateFlow(SettingUiState())
    override val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    override val themeNum = mutableStateOf(Theme.AUTO.num)
    override val fontType = mutableStateOf(FontType.DEFAULT)

    private val fontTypeFlow: StateFlow<String> = dataStoreRepository
        .selectedFontType()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = FontType.DEFAULT.fontName,
        )

    private val themeNumFlow: StateFlow<Int> = dataStoreRepository
        .selectedThemeNum()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = Theme.AUTO.num,
        )

    init {
        themeNum.value = themeNumFlow.value
        fontType.value = when (fontTypeFlow.value) {
            FontType.DEFAULT.fontName -> FontType.DEFAULT
            FontType.ROCKN_ROLL_ONE.fontName -> FontType.ROCKN_ROLL_ONE
            FontType.ROBOTO_SLAB.fontName -> FontType.ROBOTO_SLAB
            FontType.PACIFICO.fontName -> FontType.PACIFICO
            FontType.HACHI_MARU_POP.fontName -> FontType.HACHI_MARU_POP
            else -> {
                Timber.e("定義されていないフォント: $fontType")
                FontType.DEFAULT
            }
        }
    }

    override fun updateThemeNum(newThemeNum: Int) {
        themeNum.value = newThemeNum
        CoroutineScope(ioDispatcher).launch {
            dataStoreRepository.updateThemeNum(newThemeNum)
        }
    }

    override fun updateFontType(newFontType: FontType) {
        fontType.value = newFontType
        CoroutineScope(ioDispatcher).launch {
            dataStoreRepository.updateFontType(newFontType = newFontType)
        }
    }

    override fun isSelectedThemeNum(themeNum: Int): Boolean {
        return this.themeNum.value == themeNum
    }

    override fun isSelectedFont(fontType: FontType): Boolean {
        return this.fontType.value == fontType
    }

    override fun openConfirmDialog() {
        _uiState.update { it.copy(isShowConfirmDialog = true) }
    }

    override fun closeConfirmDialog() {
        _uiState.update { it.copy(isShowConfirmDialog = false) }
    }

    override fun openSelectLanguageDialog() {
        _uiState.update { it.copy(isShowSelectLanguageDialog = true) }
    }

    override fun closeSelectLanguageDialog() {
        _uiState.update { it.copy(isShowSelectLanguageDialog = false) }
    }

    override suspend fun deleteAllData() {
        CoroutineScope(ioDispatcher).launch {
            colorRepository.deleteAllColors()
            colorRepository.deleteAllCategories()
        }
    }
}
