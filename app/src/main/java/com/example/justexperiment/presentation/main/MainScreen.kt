package com.example.justexperiment.presentation.main


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.justexperiment.presentation.common.JustSearchScreen
import com.example.justexperiment.presentation.navigation.Route
import com.example.justexperiment.presentation.utils.Content
import com.example.justexperiment.presentation.utils.contentList
import com.example.justexperiment.presentation.utils.showToast


@Composable
fun MainScreen(navController: NavController) {

    val context = LocalContext.current
    var hasSearchClicked by remember { mutableStateOf(false) }
    val searchViewmodel: SearchViewModel = viewModel()
    val searchState by searchViewmodel.state.collectAsState()


    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = CircleShape
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth(0.8f)
                            .clickable(onClick = { hasSearchClicked = true })
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Rounded.Search, "search")
                        Spacer(Modifier.size(12.dp))
                        Text("Search content by title")
                    }

                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(contentList) { content ->
                ContentCard(
                    content = content,
                    onContentClick = {
                        if (content.route == null) {
                            context.showToast("No navigation found")
                        } else {
                            navController.navigate(content.route)
                        }
                    }
                )
            }
        }
    }

    if (hasSearchClicked){
        JustSearchScreen(
            searchText = searchState.searchText,
            searchedItems = searchState.searchedItems,
            onSearchBarTextChange = { query ->
                searchViewmodel.onEvent(
                    SearchEvent.OnSearchBarTextChange(query)
                )
            },
            onSearchedItemClick = { content ->
                if (content.route == null) {
                    context.showToast("No navigation found")
                } else {
                    navController.navigate(content.route)
                }
            },
            onCancel = { hasSearchClicked = false }
        )
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
        Route.CensoredScreen(0)
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


