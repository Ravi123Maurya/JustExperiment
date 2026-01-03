package com.example.justexperiment.presentation.common

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import org.junit.rules.Verifier


@Preview(showBackground = true)
@Composable
fun FilterRail(
    modifier: Modifier = Modifier,
    filterTitle: String = "Shapes",
    filters: List<String> = listOf("Circle", "Square", "Rectangle", "Triangle"),
    selectedFilter: String = "Circle",
    onFilterClick: (String) -> Unit = {}
) {
    LazyRow(modifier = modifier.height(IntrinsicSize.Max)) {
        item {
            Text(
                text = filterTitle,
            )
            VerticalDivider()
        }

        items(filters) { filter ->
            FilterTextButton(
                isSelected = filter == selectedFilter,
                filterText = filter,
                onClick = {
                    onFilterClick(filter)
                }
            )
        }

    }
}

@Composable
private fun FilterTextButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    filterText: String,
    onClick: () -> Unit
) {

    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.background
        )
    ) {
        Text(
            text = filterText
        )
    }

}