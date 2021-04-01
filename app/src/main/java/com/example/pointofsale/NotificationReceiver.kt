package com.example.pointofsale

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.pointofsale.fragments.fragment_list

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        fragment_list.passData("notif")
    }
}