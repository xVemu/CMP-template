package pl.inno4med.asystent.di

import org.koin.core.annotation.Single

@Single
actual class InAppReviewHelper {
    actual suspend fun review() {
    }
}
