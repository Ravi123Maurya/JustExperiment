package com.example.justexperiment.presentation.contents.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Route
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.justexperiment.presentation.navigation.Route
import com.example.justexperiment.presentation.utils.Content

val navigation = Content(
    id = 743858,
    title = "Navigation",
    description = "Learn navigation in jetpack compose",
    icon = Icons.Rounded.Route,
    route = Route.NavigationScreen(743858)
)

@Composable
fun NavigationScreen(
    id: Int,
    navController: NavController
){

}