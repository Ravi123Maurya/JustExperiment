package com.example.justexperiment.presentation.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed interface Route {
    @Serializable
    object MainScreen : Route


    @Serializable
    data class CensoredScreen(
        val contentId: Int
    ) : Route

    @Serializable
    data class AnimationScreen(
        val contentId: Int
    ) : Route
    @Serializable
    data class NavigationScreen(
        val contentId: Int
    ) : Route

    @Serializable
    data class PathLineScreen(
        val contentId: Int
    ) : Route

    @Serializable
    data class BroadcastScreen(
        val contentId: Int
    ) : Route

    @Serializable
    data class InAppPurchaseScreen(
        val contentId: Int
    ) : Route
}

