package com.example.pointofsale

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentManager
import com.example.pointofsale.fragments.fragment_list
import java.util.*

class alarmReceiver : BroadcastReceiver() {
    private var mAlarmManager: AlarmManager? = null
    private var mPendingIntent:PendingIntent? = null

    override fun onReceive(context: Context, intent: Intent) {

        intent?.apply{
            val id = getIntExtra(EXTRA_ID, baseNotification.NOTIFICATION_ID)
            NotificationManagerCompat.from(context!!).cancel(id!!)
        }

        // alarm manager
        mAlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var alarmTimer = Calendar.getInstance()
        alarmTimer.add(Calendar.SECOND,5)

        var data = intent.getParcelableExtra<Parcelable>(EXTRA_DATA)
        var bytes = ParcelableUtil.marshall(data)

        var sendIntent = Intent(context, ChannelAndNotifReceiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(EXTRA_DATA, bytes)
        }

        mPendingIntent = PendingIntent.getBroadcast(context, 801, sendIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        mAlarmManager?.set(AlarmManager.RTC, alarmTimer.timeInMillis, mPendingIntent)
    }
}