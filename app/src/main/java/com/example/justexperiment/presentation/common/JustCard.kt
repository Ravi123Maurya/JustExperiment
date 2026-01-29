package com.example.justexperiment.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.justexperiment.presentation.utils.JustPaddings
import com.example.justexperiment.ui.theme.JustExperimentTheme


@Composable
fun JustCard(
    modifier: Modifier = Modifier.padding(12.dp),
    contentPadding: PaddingValues = PaddingValues(12.dp),
    cardElevation: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedCard(
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.elevatedCardElevation(cardElevation)
        ) {
            Box(modifier = Modifier.padding(contentPadding)) {
                content()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JustCardPreview() {
    JustExperimentTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            JustCard(modifier = Modifier
                .fillMaxWidth()
                .padding()) {
                Box(Modifier
                    .fillMaxWidth()
                    .height(300.dp)) {
                    Text("Hello Card")
                }
            }
        }
    }
}