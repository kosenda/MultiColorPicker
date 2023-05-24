package kosenda.makecolor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.core.data.repository.DataStoreRepository
import kosenda.makecolor.core.util.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

const val MAX_COUNT_FOR_REVIEW = 1
const val COMPLETED_REVIEW = -1

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    var isShowRequestReviewDialog by mutableStateOf(false)
        private set

    val themeNum: Flow<Int> = dataStoreRepository.selectedThemeNum()
    val customFont: Flow<String> = dataStoreRepository.selectedFontType()

    fun checkCountForReview(onReachMaxCount: () -> Unit) {
        CoroutineScope(ioDispatcher).launch {
            val countForReview = dataStoreRepository.fetchCountForReview().firstOrNull()
            if (countForReview == null) {
                dataStoreRepository.updateCountForReview(0)
            } else {
                Timber.d("countForReview: $countForReview")
                if (countForReview == COMPLETED_REVIEW) return@launch
                if (countForReview > MAX_COUNT_FOR_REVIEW) {
                    onReachMaxCount()
                } else {
                    dataStoreRepository.updateCountForReview(countForReview + 1)
                }
            }
        }
    }

    fun updateCountForReview(isCompletedReview: Boolean) {
        CoroutineScope(ioDispatcher).launch {
            if (isCompletedReview) {
                dataStoreRepository.updateCountForReview(COMPLETED_REVIEW)
            } else {
                dataStoreRepository.updateCountForReview(0)
            }
        }
    }

    fun showReviewDialog() {
        isShowRequestReviewDialog = true
    }

    fun closeReviewDialog() {
        isShowRequestReviewDialog = false
    }
}
