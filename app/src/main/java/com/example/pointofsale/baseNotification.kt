package com.example.pointofsale

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class baseNotification :Application(){
    companion object{
        const val channel_1_ID = "Channel1"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel1 = NotificationChannel(channel_1_ID,"Stock Alert", NotificationManager.IMPORTANCE_HIGH)
            channel1.description = "Channel Stock"

            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel1)
        }

    }
}