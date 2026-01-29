package com.example.justexperiment.presentation.contents.broadcastreceiver.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class AirplaneModeReceiver : BroadcastReceiver() {

    val isAirplaneModeOn = MutableStateFlow(false)

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED){
            isAirplaneModeOn.value = intent.getBooleanExtra("state", false)
            Toast.makeText(context, "Airplane Mode changed: ${isAirplaneModeOn.value}", Toast.LENGTH_SHORT).show()
        }
    }


}