package com.example.pointofsale

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.view.View
import androidx.core.app.NotificationManagerCompat

class baseNotification :Application(){
    companion object{
        const val CHANNEL_1_ID = "Channel1"
        const val NOTIFICATION_ID = 101
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel1 = NotificationChannel(CHANNEL_1_ID,"Stock", NotificationManager.IMPORTANCE_HIGH)
            channel1.description = "Channel Stock"

            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel1)
        }

    }
}