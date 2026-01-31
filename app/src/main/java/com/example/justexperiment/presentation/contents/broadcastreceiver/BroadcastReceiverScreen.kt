package com.example.justexperiment.presentation.contents.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AirplanemodeActive
import androidx.compose.material.icons.rounded.AirplanemodeInactive
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.boswelja.markdown.material3.MarkdownDocument
import com.example.justexperiment.R
import com.example.justexperiment.presentation.common.ContentView
import com.example.justexperiment.presentation.common.JustBigIconButton
import com.example.justexperiment.presentation.common.JustCard
import com.example.justexperiment.presentation.navigation.Route
import com.example.justexperiment.presentation.utils.Content
import com.example.justexperiment.presentation.utils.clickableNoRipple
import com.example.justexperiment.presentation.utils.showToast

val broadcastReceiver = Content(
    id = 123877,
    title = "Broadcast Receiver",
    description = "Understanding broadcast receiver in android.",
    icon = Icons.Rounded.AirplanemodeActive,
    route = Route.BroadcastScreen(123877)
)

@Composable
fun BroadcastReceiverScreen(
    id: Int,
    navController: NavController,
) {

    ContentView(
        id = id,
        onNavBack = { navController.popBackStack() }
    ) {
        BroadcastContents()
    }
}

@Composable
private fun BroadcastContents() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JustAirplaneMode()
    }
}

@Composable
private fun JustAirplaneMode() {

    val context = LocalContext.current

    var isAirplaneModeOn by remember { mutableStateOf(false) }

    val airReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(
                context: Context?,
                intent: Intent?
            ) {
                isAirplaneModeOn = intent?.getBooleanExtra("state", false) ?: false
            }

        }
    }

    DisposableEffect(Unit) {
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).apply {
            ContextCompat.registerReceiver(
                context,
                airReceiver,
                this,
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
        }

        onDispose {
            context.unregisterReceiver(airReceiver)
        }
    }

    JustCard {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                JustBigIconButton(
                    icon = if (isAirplaneModeOn) Icons.Rounded.AirplanemodeActive else Icons.Rounded.AirplanemodeInactive
                ) { }
                Spacer(Modifier.size(12.dp))
                Text(
                    text = if (isAirplaneModeOn) "Airplane is enabled" else "Airplane mode is disabled"
                )
            }

            MarkdownDocument(
                markdown = "In Android app development, a **BroadcastReceiver** is a component that responds " +
                        "to system-wide or app-specific broadcast messages. These messages can come from" +
                        " other applications, system events like network changes, battery level updates," +
                        " or custom broadcasts from your own app."
            )

        }

    }
}


