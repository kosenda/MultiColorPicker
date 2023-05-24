package kosenda.makecolor

import android.app.Activity
import android.content.Context
import com.google.android.play.core.review.ReviewManagerFactory
import timber.log.Timber

class ReviewManager(
    context: Context,
    private val onComplete: () -> Unit,
    private val onCancel: () -> Unit,
) {
    private val activity = context as Activity
    private val manager = ReviewManagerFactory.create(context)

    fun startReviewFlow() {
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                manager.launchReviewFlow(activity, reviewInfo).apply {
                    addOnCompleteListener {
                        when (it.result) {
                            null -> onCancel()
                            else -> onComplete()
                        }
                    }
                }
            } else {
                Timber.e("task.exception ${task.exception}")
            }
        }
    }
}
