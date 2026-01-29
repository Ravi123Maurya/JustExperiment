package com.example.justexperiment.presentation.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier


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
fun Modifier.clickableW(onClick: () -> Unit) : Modifier{
    return this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick
    )
}