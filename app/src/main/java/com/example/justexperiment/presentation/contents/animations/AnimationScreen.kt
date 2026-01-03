package com.example.justexperiment.presentation.contents.animations

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.justexperiment.presentation.common.ContentView
import com.example.justexperiment.presentation.common.EmptyContent
import com.example.justexperiment.presentation.common.NavBackScaffold
import com.example.justexperiment.presentation.utils.contentList


@Composable
fun AnimationScreen(
    id: Int,
    navController: NavController
) {

    AnimationScreenContent(0)

}

@Composable
fun AnimationScreenContent(
    id: Int,
) {

    ContentView(id) {

    }

}
