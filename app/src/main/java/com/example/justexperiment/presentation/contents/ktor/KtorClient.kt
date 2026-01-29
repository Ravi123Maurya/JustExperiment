package com.example.justexperiment.presentation.contents.ktor

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.auth.HttpAuthHeader
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient {

    companion object{
        fun getClient() : HttpClient{
            return HttpClient {

                install(Logging){

                }

                install(ContentNegotiation){
                    json(
                        json = Json {
                            ignoreUnknownKeys = true
                        }
                    )
                }

            }
        }
    }

}