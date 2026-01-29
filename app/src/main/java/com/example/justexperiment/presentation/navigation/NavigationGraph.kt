package com.example.justexperiment.presentation.navigation

import android.content.BroadcastReceiver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.justexperiment.presentation.contents.animations.AnimationScreen
import com.example.justexperiment.presentation.contents.broadcastreceiver.BroadcastReceiverScreen
import com.example.justexperiment.presentation.contents.broadcastreceiver.util.AirplaneModeReceiver
import com.example.justexperiment.presentation.contents.navigation.NavigationScreen
import com.example.justexperiment.presentation.contents.pathline.PathLineScreen
import com.example.justexperiment.presentation.contents.ktor.CensoredTextScreen
import com.example.justexperiment.presentation.contents.ktor.KtorClient
import com.example.justexperiment.presentation.contents.ktor.NetworkDataRepositoryImpl
import com.example.justexperiment.presentation.main.MainScreen


@Composable
fun NavigationGraph(navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = Route.MainScreen
    ){
        composable<Route.MainScreen>{
            MainScreen(navController)
        }

        composable<Route.AnimationScreen> {
            val args = it.toRoute<Route.AnimationScreen>()
            AnimationScreen(args.contentId, navController)
        }
        composable<Route.NavigationScreen> {
            val args = it.toRoute<Route.NavigationScreen>()
            NavigationScreen(args.contentId, navController)
        }
        composable<Route.PathLineScreen> {
            val args = it.toRoute<Route.PathLineScreen>()
            PathLineScreen(args.contentId, navController)
        }

        composable<Route.CensoredScreen> {
            val args = it.toRoute<Route.CensoredScreen>()
            CensoredTextScreen(
                contentId = args.contentId,
                navController = navController,
                networkRepository = remember { NetworkDataRepositoryImpl(KtorClient.getClient()) }
            )
        }
        composable<Route.BroadcastScreen> {
            val args = it.toRoute<Route.BroadcastScreen>()
            BroadcastReceiverScreen(args.contentId, navController)
        }

    }
}