package com.example.pointofsale.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import com.example.pointofsale.EXTRA_ID
import com.example.pointofsale.baseNotification

class notificationReceiver : BroadcastReceiver(){
    override fun onReceive(p0: Context?, p1: Intent?) {
        var id = p1?.getIntExtra(EXTRA_ID, baseNotification.NOTIFICATION_ID)
        NotificationManagerCompat.from(p0!!).cancel(id!!);
    }

}