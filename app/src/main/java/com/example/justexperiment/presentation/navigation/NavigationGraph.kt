package com.example.justexperiment.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.justexperiment.presentation.ktor.CensoredTextScreen
import com.example.justexperiment.presentation.ktor.KtorClient
import com.example.justexperiment.presentation.ktor.NetworkDataRepositoryImpl
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

        composable< Route.CensorScreen> {
            val args = it.toRoute<Route.CensorScreen>()
            CensoredTextScreen(
                contentId = args.id,
                navController = navController,
                networkRepository = remember { NetworkDataRepositoryImpl(KtorClient.getClient()) }
            )
        }

    }
}