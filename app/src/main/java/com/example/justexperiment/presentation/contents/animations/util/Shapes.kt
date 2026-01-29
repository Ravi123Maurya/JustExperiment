package com.example.justexperiment.presentation.contents.animations.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

enum class Shapes(name: String) {
    CIRCLE("Circle"),
    SQUARE("Square"),
    RECTANGLE("Rectangle"),
    TRIANGLE("Triangle"),
}

@Composable
fun Circle(
    modifier: Modifier = Modifier,
    text: String? = null,
    color: Color = Color.Red,
    brushColor: Brush? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .drawBehind {
                if (brushColor != null) {
                    drawCircle(
                        brush = brushColor,
                        radius = size.width / 2
                    )
                } else {
                    drawCircle(
                        color = color,
                        radius = size.width / 2
                    )
                }
            }
            .clickable { onClick() }
    ) {
        text?.let { Text(text) }
    }
}

@Composable
fun Square(
    modifier: Modifier = Modifier,
    text: String? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .drawBehind {
                drawRect(
                    color = Color.Red,
                    size = size
                )
            }
            .clickable { onClick() }
    ) {
        text?.let { Text(text) }

    }
}

@Composable
fun Rectangle(
    modifier: Modifier = Modifier,
    text: String? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(150.dp, 100.dp)
            .drawBehind {
                drawRect(
                    color = Color.Red,
                    size = size
                )
            }
            .clickable { onClick() }

    ) {
        text?.let { Text(text) }
    }
}

@Composable
fun Triangle(
    modifier: Modifier = Modifier,
    text: String? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(100.dp, 100.dp)
            .drawBehind {

                val path = Path().apply {
                    moveTo(size.width / 2f, 0f)
                    lineTo(0f, size.height)
                    lineTo(size.width, size.height)
                    close()
                }

                drawPath(
                    path = path,
                    color = Color.Red
                )
            }
            .clickable { onClick() }

    ) {
        text?.let { Text(text) }
    }
}
