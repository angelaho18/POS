package com.example.pointofsale

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.core.app.NotificationManagerCompat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    private var mAlarmManager: AlarmManager? = null
    private var mPendingIntent: PendingIntent? = null

    override fun onReceive(context: Context, intent: Intent) {

        intent.apply{
            val id = getIntExtra(EXTRA_ID, BaseNotification.NOTIFICATION_ID)
            NotificationManagerCompat.from(context).cancel(id)
        }

        // alarm manager
        mAlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var alarmTimer = Calendar.getInstance()
        alarmTimer.add(Calendar.MINUTE,15)

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