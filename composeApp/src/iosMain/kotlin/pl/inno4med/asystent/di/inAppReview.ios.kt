package pl.inno4med.asystent.di

import com.mikhailovskii.inappreview.appStore.AppStoreInAppReviewInitParams
import com.mikhailovskii.inappreview.appStore.AppStoreInAppReviewManager
import kotlinx.coroutines.flow.singleOrNull
import org.koin.core.annotation.Single

@Single
actual class InAppReviewHelper {
    private val delegate = AppStoreInAppReviewManager(
        AppStoreInAppReviewInitParams("TODO") // TODO
    )

    actual suspend fun review() {
        delegate.requestInAppReview().singleOrNull()
    }
}
