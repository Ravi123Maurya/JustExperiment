package com.example.justexperiment.presentation.utils

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class JustPaddings(
    val tiny: Dp = 2.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 12.dp,
    val large: Dp = 16.dp,
    val extraLarge: Dp = 24.dp,
    val extraExtraLarge: Dp = 32.dp,
){
    companion object{
        val ZERO = 0.dp
    }
}

val JustLocal = staticCompositionLocalOf<JustPaddings> { error("Paddings must be set!") }

