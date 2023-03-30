package kosenda.makecolor.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavBackStackEntry
import com.godaddy.android.colorpicker.HsvColor
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import kosenda.makecolor.model.Category
import kosenda.makecolor.model.StringResource
import kosenda.makecolor.model.default.webColor
import kosenda.makecolor.view.CMYKColor
import kosenda.makecolor.view.FontType
import kosenda.makecolor.view.HSVColor
import kosenda.makecolor.view.RGBColor
import kosenda.makecolor.view.Theme
import kosenda.makecolor.view.code.ColorIndex
import kosenda.makecolor.view.code.PickerType
import kosenda.makecolor.view.state.CategoryDetailUiState
import kosenda.makecolor.view.state.ColorDetailUiState
import kosenda.makecolor.view.state.DataUiState
import kosenda.makecolor.view.state.GradationUiState
import kosenda.makecolor.view.state.InputTextUiState
import kosenda.makecolor.view.state.MergeUiState
import kosenda.makecolor.view.state.PickerUiState
import kosenda.makecolor.view.state.PictureUiState
import kosenda.makecolor.view.state.RandomUiState
import kosenda.makecolor.view.state.RegisterUiState
import kosenda.makecolor.view.state.SeekbarUiState
import kosenda.makecolor.view.state.SelectColorUiState
import kosenda.makecolor.view.state.SettingUiState
import kosenda.makecolor.view.state.SplitUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PreviewCategoryDetailViewModel : CategoryDetailViewModel() {
    override val uiState: StateFlow<CategoryDetailUiState> =
        MutableStateFlow(CategoryDetailUiState())
    override fun updateCategory(newCategory: Category) {}
    override fun deleteCategory() {}
    override fun closeNewCategoryDialog() {}
    override fun openNewCategoryDialog() {}
}

class PreviewColorDetailViewModel : ColorDetailViewModel() {
    override val uiState: StateFlow<ColorDetailUiState> = MutableStateFlow(ColorDetailUiState())
}

class PreviewDataViewModel : DataViewModel() {
    override val uiState: StateFlow<DataUiState> = MutableStateFlow(
        DataUiState(categories = listOf(Category(name = "temp", size = 1))),
    )
    override fun fetchCategories(defaultCategories: List<Category>) {}
}

class PreviewGradationViewModel : GradationViewModel() {
    override val uiState: StateFlow<GradationUiState> = MutableStateFlow(GradationUiState())
    override fun init(navBackStackEntry: NavBackStackEntry?) {}
    override fun fetchCategories(defaultCategories: List<Category>) {}
    override fun updateSelectCategory1(index: Int) {}
    override fun updateSelectCategory2(index: Int) {}
}

class PreviewInputTextViewModel : InputTextViewModel() {
    override val uiState: StateFlow<InputTextUiState> = MutableStateFlow(InputTextUiState())
    override fun updateSelectColorType(index: Int) {}
    override fun updateInputText(rgbType: RGBColor, value: String): StringResource? = null
    override fun updateInputText(cmykType: CMYKColor, value: String): StringResource? = null
    override fun updateInputText(hsvType: HSVColor, value: String): StringResource? = null
    override fun updateInputText(hex: String): StringResource? = null
}

class PreviewMergeViewModel : MergeViewModel() {
    override val uiState: StateFlow<MergeUiState> = MutableStateFlow(MergeUiState())
    override fun init(navBackStackEntry: NavBackStackEntry?) {}
    override fun fetchCategories(defaultCategories: List<Category>) {}
    override fun updateColorData(color: Color) {}
    override fun resetBitmap() {}
    override fun createBitmap(
        hex1: String,
        hex2: String,
        btmHeight: Float,
        btmWidth: Float,
        density: Density,
        layoutDirection: LayoutDirection,
    ) {}
    override fun updateSelectCategory1(index: Int) {}
    override fun updateSelectCategory2(index: Int) {}
}

class PreviewPickerViewModel : PickerViewModel() {
    override val uiState: StateFlow<PickerUiState> = MutableStateFlow(PickerUiState())
    override fun updateHsvColor(color: HsvColor) {}
    override fun updateColorData(color: HsvColor) {}
    override fun updatePickerType(type: PickerType) {}
    override fun updateHarmonyMode(mode: ColorHarmonyMode) {}
}

class PreviewPictureViewModel : PictureViewModel() {
    override val uiState: StateFlow<PictureUiState> = MutableStateFlow(PictureUiState())
    override fun updateColorData(color: Color) {}
    override fun makeBitmapAndPalette(uri: Uri, context: Context) {}
    override fun resetImage() {}
}

class PreviewRandomViewModel : RandomViewModel() {
    override val uiState: StateFlow<RandomUiState> = MutableStateFlow(RandomUiState())
    override fun updateColorData(color: Color) {}
    override fun updateRandomType(index: Int) {}
    override fun outputRandomColors() {}
}

class PreviewRegisterViewModel : RegisterViewModel() {
    override val uiState: StateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState())
    override fun updateMemo(memo: String) {}
    override fun fetchCategories() {}
    override fun changeSelectCategory(index: Int) {}
    override fun addCategory(newCategory: Category) {}
    override fun closeAddCategoryDialog() {}
    override fun openAddCategoryDialog() {}
    override fun registerColor(hex: String, context: Context) {}
}

class PreviewSeekbarViewModel : SeekbarViewModel() {
    override val uiState: StateFlow<SeekbarUiState> = MutableStateFlow(SeekbarUiState())
    override fun updateSelectColorType(index: Int) {}
    override fun updateColorData(rgbType: RGBColor, value: Float) {}
    override fun updateColorData(cmykType: CMYKColor, value: Float) {}
    override fun updateColorData(hsvType: HSVColor, value: Float) {}
}

class PreviewSelectColorViewModel : SelectColorViewModel() {
    override val uiState: StateFlow<SelectColorUiState> = MutableStateFlow(
        SelectColorUiState(colors = webColor),
    )
    override fun getColors() {}
    override fun deleteColorItem(deleteColorItem: kosenda.makecolor.model.data.ColorItem) {}
}

class PreviewSettingsViewModel : SettingsViewModel() {
    override val uiState: StateFlow<SettingUiState> = MutableStateFlow(SettingUiState())
    override val fontType: MutableState<FontType> = mutableStateOf(FontType.DEFAULT)
    override val themeNum: MutableState<Int> = mutableStateOf(Theme.AUTO.num)
    override fun updateThemeNum(newThemeNum: Int) {}
    override fun updateFontType(newFontType: FontType) {}
    override fun isSelectedThemeNum(themeNum: Int): Boolean = true
    override fun isSelectedFont(fontType: FontType): Boolean = true
    override fun openConfirmDialog() {}
    override fun closeConfirmDialog() {}
    override fun openSelectLanguageDialog() {}
    override fun closeSelectLanguageDialog() {}
    override suspend fun deleteAllData() {}
}

class PreviewSplitViewModel : SplitViewModel() {
    override val uiState: StateFlow<SplitUiState> = MutableStateFlow(SplitUiState())
    override fun init(navBackStackEntry: NavBackStackEntry?) {}
    override fun fetchCategories(defaultCategories: List<Category>) {}
    override fun updateSelectSplitColorNum(index: Int) {}
    override fun updateSelectCategory(categoryIndex: Int, colorIndex: ColorIndex) {}
}
