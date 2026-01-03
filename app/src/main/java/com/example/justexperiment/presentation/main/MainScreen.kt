package com.example.justexperiment.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.justexperiment.presentation.navigation.Route
import com.example.justexperiment.presentation.utils.Content
import com.example.justexperiment.presentation.utils.contentList


@Composable
fun MainScreen(navController: NavController) {

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(contentList) { content ->
                ContentCard(
                    content = content,
                    onContentClick = {
                        navController.navigate(content.route)
                    }
                )
            }
        }
    }


}

@Preview
@Composable
fun ContentCard(
    content: Content = Content(
        0,
        "Animations",
        "Cool color, size, and shape animations",
        Icons.Default.Category,
        Route.CensorScreen(0)
    ),
    onContentClick: () -> Unit = {}
) {
    Card(
        onClick = onContentClick
    ) {
        ListItem(
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(content.color.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = content.color
                    )
                }

            },
            headlineContent = { Text(text = content.title) },
            supportingContent = { Text(text = content.description) }
        )
    }
}


