package com.example.justexperiment.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.justexperiment.R
import com.example.justexperiment.presentation.main.ContentCard
import com.example.justexperiment.presentation.utils.Content
import com.example.justexperiment.presentation.utils.contentList

@Composable
fun JustSearchScreen(
    searchText: String = "",
    searchedItems: List<Content> = contentList,
    onSearchBarTextChange: (String) -> Unit = {},
    onSearchedItemClick: (Content) -> Unit = {},
    onCancel: () -> Unit = {}
) {

    Surface(
        modifier = Modifier
            .systemBarsPadding()
            .navigationBarsPadding(),
        color = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.7f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier.padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        modifier = Modifier.weight(1f).focusTarget(),
                        value = searchText,
                        onValueChange = { onSearchBarTextChange(it) },
                        placeholder = { Text(stringResource(R.string.search_by_content))},
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = stringResource(R.string.search),
                            )
                        },
                        maxLines = 1,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = CircleShape
                    )

                    IconButton(
                        onClick = {
                            onSearchBarTextChange("")
                            onCancel()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = stringResource(R.string.clear),
                            modifier = Modifier
                                .padding(8.dp),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

            }
            HorizontalDivider()
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    items = searchedItems,
                    key = { "${it.id}" }
                ) { content ->
                    ContentCard(
                        content = content,
                        onContentClick = { onSearchedItemClick(content) }
                    )
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
private fun JustSearchPreview() {
    JustSearchScreen { }
}