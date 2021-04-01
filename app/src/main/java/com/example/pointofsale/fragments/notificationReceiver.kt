package com.example.pointofsale.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class notificationReceiver : BroadcastReceiver(){
    override fun onReceive(p0: Context?, p1: Intent?) {
        fragment_list.dismiss()
//        val message =
//        Toast.makeText(p0, message,Toast.LENGTH_LONG).show()
    }

}