package pl.inno4med.asystent.core

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldMatchInOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveCauseOfType
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import pl.inno4med.asystent.utils.Result
import kotlin.test.Test

class RemoteMediatorTest : RemoteMediator {
    @Test
    fun `should loading then success`() = runTest {
        val flow = sync(
            getLocal = null,
            getRemote = { "remoteData" },
            mapDtoToDomain = { it.uppercase() },
            deleteAndInsert = null,
        ).toList()

        flow.shouldContainExactly(
            Result.Loading,
            Result.Success(body = "REMOTEDATA")
        )
    }

    @Test
    fun `should emit local data then success from remote`() = runTest {

        var calledDeleteAndInsert = false

        val flow = sync(
            getLocal = { "localData" },
            getRemote = { "remoteData" },
            mapDtoToDomain = { it.uppercase() },
            deleteAndInsert = {
                calledDeleteAndInsert = true
            },
        ).toList()

        flow.shouldContainExactly(
            Result.Success(body = "localData", refreshing = true),
            Result.Success(body = "REMOTEDATA")
        )

        calledDeleteAndInsert shouldBe true
    }

    @Test
    fun `should loading then failure`() = runTest {
        val flow = sync<String, String>(
            getLocal = null,
            getRemote = { throw RuntimeException("test") },
            mapDtoToDomain = { it.uppercase() },
            deleteAndInsert = null,
        ).toList()

        flow.shouldMatchInOrder({ loading ->
            loading shouldBe Result.Loading
        }, { failure ->
            failure.shouldBeInstanceOf<Result.Failure>()
            failure.error.shouldHaveCauseOfType<RuntimeException>()
            failure.error.message shouldBe "test"
        })
    }

    @Test
    fun `should loading then success on db error`() = runTest {
        val flow = sync(
            getLocal = { throw RuntimeException("db error") },
            getRemote = { "remoteData" },
            mapDtoToDomain = { it.uppercase() },
            deleteAndInsert = { throw RuntimeException("db error") },
        ).toList()

        flow.shouldContainExactly(
            Result.Loading,
            Result.Success(body = "REMOTEDATA")
        )
    }

    @Test
    fun `should emit local data then failure from remote`() = runTest {
        val flow = sync(
            getLocal = { listOf(1, 2, 3) },
            getRemote = { throw RuntimeException("network error") },
            mapDtoToDomain = { it },
            deleteAndInsert = null,
        ).toList()

        flow.shouldMatchInOrder({ success ->
            success shouldBe Result.Success(body = listOf(1, 2, 3), refreshing = true)
        }, { success ->
            success.asSuccess!!.body shouldBe listOf(1, 2, 3)
            success.asSuccess.error!!.shouldHaveCauseOfType<RuntimeException>()
            success.asSuccess.error.message shouldBe "network error"
        })
    }

    @Test
    fun `remote should be cancelled`() = runTest {
        var cancelled = false

        val flow = sync(
            getLocal = null,
            getRemote = {
                try {
                    delay(Long.MAX_VALUE)
                    "remoteData"
                } catch (e: CancellationException) {
                    cancelled = true
                    throw e
                }
            },
            mapDtoToDomain = { it.uppercase() },
            deleteAndInsert = null,
        )

        val collectJob = flow.launchIn(this)

        delay(100) // Give some time to start

        collectJob.cancelAndJoin()

        cancelled shouldBe true
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should download data async`() = runTest {
        sync(
            getLocal = {
                delay(1000)
            },
            getRemote = {
                delay(1000)
            },
            mapDtoToDomain = { },
            deleteAndInsert = null,
        ).collect()

        currentTime shouldBe 1000
    }
}
