package com.example.pointofsale.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.example.pointofsale.EXTRA_ID
import com.example.pointofsale.BaseNotification
import com.example.pointofsale.count

class notificationReceiver : BroadcastReceiver(){
    override fun onReceive(p0: Context?, p1: Intent?) {
        p1?.apply{
            val id = getIntExtra(EXTRA_ID, BaseNotification.NOTIFICATION_ID)
            NotificationManagerCompat.from(p0!!).cancel(id!!);
            count--
        }

        if (count < 1) NotificationManagerCompat.from(p0!!).cancel(BaseNotification.NOTIFICATION_ID)
    }
}