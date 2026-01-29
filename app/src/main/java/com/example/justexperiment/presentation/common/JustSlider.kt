package com.example.justexperiment.presentation.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun JustSlider(
    modifier: Modifier = Modifier,
    label: String? = "null",
    value: Float = 0f,
    onValueChange: (Float) -> Unit = {},
    steps: Int = 0,
    range: ClosedFloatingPointRange<Float> = 0f..30f,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Slider(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = { onValueChange(it) },
            valueRange = range,
            steps = steps
        )

        Spacer(Modifier.size(12.dp))
        label?.let { Text(it) }
    }
}