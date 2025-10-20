package pl.inno4med.asystent.di

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class NetworkModule {
    @Single
    fun provideHttpClient() = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }
    }

    @Single
    fun provideRetrofit(httpClient: HttpClient) = Ktorfit.Builder()
        .baseUrl("https://api.sampleapis.com/")
        .httpClient(httpClient)
        .build()
}
