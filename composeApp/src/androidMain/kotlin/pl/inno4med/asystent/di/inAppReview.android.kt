package pl.inno4med.asystent.di

import com.mikhailovskii.inappreview.googlePlay.GooglePlayInAppReviewInitParams
import com.mikhailovskii.inappreview.googlePlay.GooglePlayInAppReviewManager
import kotlinx.coroutines.flow.singleOrNull
import org.koin.core.annotation.Single
import pl.inno4med.asystent.MainActivity

@Single
actual class InAppReviewHelper {
    private val delegate = MainActivity.instance?.let { activity ->
        GooglePlayInAppReviewManager(
            GooglePlayInAppReviewInitParams(activity)
        )
    }

    actual suspend fun review() {
        delegate?.requestInAppReview()?.singleOrNull()
    }
}
