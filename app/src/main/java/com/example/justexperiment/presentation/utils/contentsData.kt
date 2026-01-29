package com.example.justexperiment.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.justexperiment.presentation.contents.animations.animation
import com.example.justexperiment.presentation.contents.broadcastreceiver.broadcastReceiver
import com.example.justexperiment.presentation.contents.pathline.pathLine
import com.example.justexperiment.presentation.navigation.Route

val ktorLearn = Content(
    689245,
    "Ktor - Censoring",
    "Censor text using Ktor library for Api call",
    Icons.Default.Category,
    route = Route.CensoredScreen(689245)
)

val contentList = listOf(
    animation,
    ktorLearn,
    pathLine,
    broadcastReceiver
)


data class Content(
    val id: Int,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val route: Route?,
    val color: Color = Color.Blue
)