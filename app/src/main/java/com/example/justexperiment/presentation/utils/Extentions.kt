package com.example.justexperiment.presentation.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp


var toast: Toast? = null
fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT){
    toast?.cancel()
    toast = Toast.makeText(this, text, duration)
    toast?.show()
}


/*
* Custom [clickableW] modifier without click indication (feedback)
*/
@Composable
fun Modifier.clickableNoRipple(onClick: () -> Unit) : Modifier{
    return this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick
    )
}

fun Modifier.heightSpacer(height: Dp) = this.padding(vertical = height)