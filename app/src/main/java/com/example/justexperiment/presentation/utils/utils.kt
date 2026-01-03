package com.example.justexperiment.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.justexperiment.presentation.navigation.Route


val contentList = listOf(
    Content(
        0,
        "Animations",
        "Cool color, size, and shape animations",
        Icons.Default.Category,
       route = Route.CensorScreen(0)
    ),
    Content(
        0,
        "Animations",
        "Cool color, size, and shape animations",
        Icons.Default.Category,
        Route.MNav(0)
    ),

)


data class Content(
    val id: Int,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val route: Route,
    val color: Color = Color.Blue
)