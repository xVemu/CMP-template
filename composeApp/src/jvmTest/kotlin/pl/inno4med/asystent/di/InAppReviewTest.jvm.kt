package pl.inno4med.asystent.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.mutablePreferencesOf
import androidx.datastore.preferences.core.preferencesOf
import dev.mokkery.annotations.DelicateMokkeryApi
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode.Companion.not
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class, DelicateMokkeryApi::class)
class InAppReviewTest {

    private val dataStore: DataStore<Preferences> = mock {
        everySuspend { updateData(any()) } returns preferencesOf()
    }

    private val clock: Clock = mock {
        every { now() } returns Instant.parse("2025-10-24T00:00:00Z")
    }

    private val key = longPreferencesKey("lastOpenTime")

    @Test
    fun `don't show on initial open`() = runTest {
        every { dataStore.data } returns flowOf(
            preferencesOf()
        )

        review(dataStore, clock)

        verifySuspend {
            dataStore.updateData(any())
        }
    }

    @Test
    fun `don't show if opened recently`() = runTest {
        every { dataStore.data } returns flowOf(
            mutablePreferencesOf(
                key to Instant.parse("2025-10-20T00:00:00Z").toEpochMilliseconds()
            )
        )

        review(dataStore, clock)

        verifySuspend(not) {
            dataStore.updateData(any())
        }
    }

    @Test
    fun `show if opened long time ago`() = runTest {
        every { dataStore.data } returns flowOf(
            mutablePreferencesOf(
                key to Instant.parse("2025-10-10T00:00:00Z").toEpochMilliseconds()
            )
        )

        review(dataStore, clock)

        verifySuspend {
            dataStore.updateData(any())
        }
    }
}
