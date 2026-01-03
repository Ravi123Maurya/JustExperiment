package com.example.justexperiment.presentation.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBackScaffold(
    modifier: Modifier = Modifier,
    title: String?,
    onNavBack: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("$title") },
                navigationIcon = {
                    IconButton(onNavBack) { Icon(Icons.Filled.ArrowBackIosNew, "go back") }
                }
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}