package pl.inno4med.asystent.core

import kotlinx.coroutines.async
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.supervisorScope
import pl.inno4med.asystent.utils.Result

interface RemoteMediator {
    /**
     * 1. Tries to get data from [getLocal] (database).
     * 2. If data is present, emits it as [Result.Success] with [Result.refreshing] = true. Otherwise emits [Result.Loading].
     * 3. Tries to get data from [getRemote] (network).
     * 4. If successful, emits it as [Result.Success] and calls [deleteAndInsert] to update local source.
     * 5. If network call fails and local data was present, emits [Result.Success] with [Result.error].
     *
     * Try not to create coroutineScope in callbacks, because this function uses supervisorScope.
     *
     * @param getLocal Function to get data from local source. Can be null if there is no local source.
     * @param getRemote Function to get data from remote source.
     * @param mapDtoToDomain Function to map data from [getRemote] to domain model.
     * @param deleteAndInsert Function to update local source with data from [getRemote]. Can be null if there is no local source.
     * */
    fun <DomainType, DtoType> sync(
        getLocal: (suspend () -> DomainType?)?,
        getRemote: suspend () -> DtoType,
        mapDtoToDomain: (DtoType) -> DomainType,
        deleteAndInsert: (suspend (dto: DtoType) -> Unit)?,
    ) = flow {
        // Can't be coroutineScope, because async can throw error
        supervisorScope early@{
            val networkDefer = async { getRemote() }

            val local = runCatching { getLocal?.invoke() }.getOrNull()

            if (local != null)
                emit(Result.Success(local, refreshing = true))
            else emit(Result.Loading)

            val network = try {
                networkDefer.await()
            } catch (e: Exception) {
                ensureActive()
                if (local != null)
                    return@early emit(Result.Success(local, error = e))

                return@early emit(Result.Failure(e))
            }

            emit(Result.Success(mapDtoToDomain(network)))

            runCatching { deleteAndInsert?.invoke(network) }
        }
    }
}
