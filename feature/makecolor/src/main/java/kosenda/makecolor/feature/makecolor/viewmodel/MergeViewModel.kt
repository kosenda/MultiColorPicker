package kosenda.makecolor.feature.makecolor.viewmodel

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.core.domain.FetchCategoriesUseCase
import kosenda.makecolor.core.domain.UpdateOtherColorUseCase
import kosenda.makecolor.core.model.data.Category
import kosenda.makecolor.core.model.data.ColorType
import kosenda.makecolor.core.ui.code.ColorIndex
import kosenda.makecolor.core.ui.state.MergeUiState
import kosenda.makecolor.core.util.colorToRGB
import kosenda.makecolor.core.util.convertDisplayStringListFromCategories
import kosenda.makecolor.core.util.hexToColor
import kosenda.makecolor.core.util.ui.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class MergeViewModel : ViewModel() {
    abstract val uiState: StateFlow<MergeUiState>
    abstract fun init(navBackStackEntry: NavBackStackEntry?)
    abstract fun fetchCategories(defaultCategories: List<Category>)
    abstract fun updateColorData(color: Color)
    abstract fun resetBitmap()
    abstract fun createBitmap(
        hex1: String,
        hex2: String,
        btmHeight: Float,
        btmWidth: Float,
        density: Density,
        layoutDirection: LayoutDirection,
    )
    abstract fun updateSelectCategory1(index: Int)
    abstract fun updateSelectCategory2(index: Int)
}

@HiltViewModel
class MergeViewModelImpl @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    val updateOtherColorUseCase: UpdateOtherColorUseCase,
    val fetchCategoriesUseCase: FetchCategoriesUseCase,
) : MergeViewModel() {
    private val _uiState = MutableStateFlow(MergeUiState())
    override val uiState: StateFlow<MergeUiState> = _uiState.asStateFlow()

    override fun init(navBackStackEntry: NavBackStackEntry?) {
        val savedStateHandle = navBackStackEntry?.savedStateHandle ?: return
        savedStateHandle.run {
            val newHex = get<String>(key = "newHex") ?: ""
            val index = get<Int>(key = "index")
            if (newHex.isNotEmpty() || index != null) {
                _uiState.update { it.copy(bitmap = null) }
                when (index) {
                    ColorIndex.FIRST.num -> _uiState.update { it.copy(selectHex1 = newHex) }
                    ColorIndex.SECOND.num -> _uiState.update { it.copy(selectHex2 = newHex) }
                    else -> throw IllegalArgumentException("Missing Index: $index")
                }
            }
        }
    }

    override fun fetchCategories(defaultCategories: List<Category>) {
        if (uiState.value.categories.isEmpty()) {
            CoroutineScope(ioDispatcher).launch {
                val categories = fetchCategoriesUseCase(defaultCategories)
                _uiState.update {
                    it.copy(
                        categories = categories,
                        selectCategory1 = categories.first(),
                        selectCategory2 = categories.first(),
                        displayCategory = convertDisplayStringListFromCategories(categories),
                    )
                }
            }
        }
    }

    override fun updateColorData(color: Color) {
        _uiState.update {
            it.copy(
                colorData = updateOtherColorUseCase(
                    colorData = it.colorData.copy(rgb = colorToRGB(color = color)),
                    type = ColorType.RGB,
                ),
            )
        }
    }

    override fun resetBitmap() = _uiState.update { it.copy(bitmap = null) }

    override fun createBitmap(
        hex1: String,
        hex2: String,
        btmHeight: Float,
        btmWidth: Float,
        density: Density,
        layoutDirection: LayoutDirection,
    ) {
        val imageBitmap = ImageBitmap(btmHeight.toInt(), (btmWidth.toInt()))
        val size = Size(btmHeight, btmWidth)
        CanvasDrawScope().draw(
            density = density,
            layoutDirection = layoutDirection,
            canvas = Canvas(imageBitmap),
            size = size,
        ) {
            drawRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        hexToColor(hex1),
                        hexToColor(hex2),
                    ),
                ),
                size = size,
            )
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color.Transparent,
                        Color.Black,
                    ),
                ),
                size = size,
                alpha = 0.75f,
            )
        }
        _uiState.update { it.copy(bitmap = imageBitmap.asAndroidBitmap()) }
    }

    override fun updateSelectCategory1(index: Int) {
        _uiState.update { it.copy(selectCategory1 = it.categories[index]) }
    }

    override fun updateSelectCategory2(index: Int) {
        _uiState.update { it.copy(selectCategory2 = it.categories[index]) }
    }
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
