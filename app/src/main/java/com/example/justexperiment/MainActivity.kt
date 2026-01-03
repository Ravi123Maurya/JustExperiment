package com.example.justexperiment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.justexperiment.ui.theme.JustExperimentTheme
import androidx.navigation.compose.rememberNavController
import com.example.justexperiment.presentation.navigation.NavigationGraph


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JustExperimentTheme {
                val navController = rememberNavController()
                NavigationGraph(navController)

            }
        }
    }
}















