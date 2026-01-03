package com.example.justexperiment.presentation.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext



@Serializable
sealed interface Route {
    @Serializable
    object MainScreen : Route


    @Serializable
    data class CensorScreen(
        val id: Int
    ) : Route

    @Serializable
    data class MNav(
        val id: Int
    ) : Route
}

