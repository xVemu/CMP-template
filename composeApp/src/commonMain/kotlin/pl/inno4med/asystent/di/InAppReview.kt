package pl.inno4med.asystent.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.architect.kmpessentials.reviews.KmpPromptReview
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.singleOrNull
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
suspend fun review(dataStore: DataStore<Preferences>) {
    val key = longPreferencesKey("lastOpenTime")
    val now = Clock.System.now()

    val lastOpenTime =
        dataStore.data.map { it[key]?.let(Instant::fromEpochMilliseconds) }.singleOrNull()
            ?: now

    if ((now - lastOpenTime) <= 7.days) return

    KmpPromptReview.promptReviewInApp(
        errorPromptingDialog = {}
    )
    dataStore.edit {
        it[key] = now.toEpochMilliseconds()
    }
}
