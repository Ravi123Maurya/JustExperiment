package com.example.justexperiment.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.justexperiment.presentation.utils.clickableW
import java.util.Scanner

@Composable
fun JustBigIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String? = null,
    tint: Color = Color.Blue,
    onClick: () -> Unit
){
    Box(
        modifier = modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(tint.copy(alpha = 0.1f))
            .clickableW(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(36.dp),
            imageVector = icon,
            contentDescription = label,
            tint = tint
        )
    }
}