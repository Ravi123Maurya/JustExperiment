package com.example.justexperiment.presentation.contents.ktor

import io.ktor.http.ContentType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException

class NetworkDataRepositoryImpl(
    private val httpClient: HttpClient
) {

    suspend fun censorWords(uncensored: String): String {
        val response = try {
            httpClient.get(
                urlString = "https://www.purgomalum.com/service/json"
            ) {
                parameter("text", uncensored)
                contentType(ContentType.Application.Json)
            }
        } catch (e: UnresolvedAddressException) {
            return "Unresolved Address Exception"
        } catch (e: SerializationException) {
            return "Serialization Exception"
        }


        return when (response.status.value) {
            in 200..299 -> {
                val result = response.body<CensoredText>()
                result.result
            }

            400 -> {
                "Something went wrong - Bad Request: 400"
            }

            404 -> {
                "Something went wrong - Not Found: 404"
            }

            500 -> {
                "Something went wrong - Internal Server Error: 500"
            }

            else -> {
                "Unknown Error"
            }
        }
    }

}

@Serializable
data class CensoredText(
    val result: String = "Default"
)