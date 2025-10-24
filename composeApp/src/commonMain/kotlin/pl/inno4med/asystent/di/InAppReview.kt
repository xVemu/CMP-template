package pl.inno4med.asystent.di

import androidx.annotation.VisibleForTesting
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.architect.kmpessentials.reviews.KmpPromptReview
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
suspend fun review(
    dataStore: DataStore<Preferences>,
    @VisibleForTesting clock: Clock = Clock.System,
) {
    val key = longPreferencesKey("lastOpenTime")
    val now = clock.now()

    val lastOpenTime =
        dataStore.data.map { it[key]?.let(Instant::fromEpochMilliseconds) }.firstOrNull()


    if (lastOpenTime != null && (now - lastOpenTime) <= 7.days) return

    KmpPromptReview.promptReviewInApp(
        errorPromptingDialog = {}
    )

    dataStore.edit {
        it[key] = now.toEpochMilliseconds()
    }
}
