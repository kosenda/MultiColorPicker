package kosenda.makecolor.feature.makecolor.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.core.domain.UpdateOtherColorUseCase
import kosenda.makecolor.core.domain.UpdatePaletteColorsUseCase
import kosenda.makecolor.core.model.data.ColorType
import kosenda.makecolor.core.ui.state.PictureUiState
import kosenda.makecolor.core.util.colorToRGB
import kosenda.makecolor.core.util.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class PictureViewModel : ViewModel() {
    abstract val uiState: StateFlow<PictureUiState>
    abstract fun updateColorData(color: Color)
    abstract fun makeBitmapAndPalette(uri: Uri, context: Context)
    abstract fun resetImage()
}

@HiltViewModel
class PictureViewModelImpl @Inject constructor(
    val updatePaletteColorsUseCase: UpdatePaletteColorsUseCase,
    val updateOtherColorUseCase: UpdateOtherColorUseCase,
    @IODispatcher val ioDispatcher: CoroutineDispatcher,
) : PictureViewModel() {
    private val _uiState = MutableStateFlow(PictureUiState())
    override val uiState: StateFlow<PictureUiState> = _uiState.asStateFlow()

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

    override fun makeBitmapAndPalette(uri: Uri, context: Context) {
        CoroutineScope(ioDispatcher).launch {
            val bitmap = Bitmap.createScaledBitmap(
                if (Build.VERSION.SDK_INT < 28) {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder
                        .decodeBitmap(source)
                        .copy(Bitmap.Config.RGBA_F16, true)
                },
                700,
                700,
                true,
            )
            _uiState.update {
                it.copy(
                    bitmap = bitmap,
                    paletteColors = updatePaletteColorsUseCase(
                        state = it.paletteColors,
                        palette = Palette.from(bitmap).generate(),
                    ),
                )
            }
        }
    }

    override fun resetImage() {
        _uiState.update {
            it.copy(
                bitmap = null,
                paletteColors = updatePaletteColorsUseCase(state = it.paletteColors),
            )
        }
    }
}

class PreviewPictureViewModel : PictureViewModel() {
    override val uiState: StateFlow<PictureUiState> = MutableStateFlow(PictureUiState())
    override fun updateColorData(color: Color) {}
    override fun makeBitmapAndPalette(uri: Uri, context: Context) {}
    override fun resetImage() {}
}