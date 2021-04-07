package com.example.pointofsale

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
            NotificationManagerCompat.from(context!!).cancel(baseNotification.NOTIFICATION_ID)
        }
        var DA = ArrayList<Product>()


        // alarm manager
        mAlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var alarmTimer = Calendar.getInstance()
        alarmTimer.add(Calendar.SECOND,5)

        var data = intent.getParcelableExtra<Product>(EXTRA_DATA)
        val bundle = Bundle()

        var sendIntent = Intent(context, ChannelAndNotifReceiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            if (data != null) {
                DA.add(data)
            }

            bundle.putParcelableArrayList("data1", DA)
            putExtras(bundle)

//            putExtra(EXTRA_DATA, DA)
            Log.d("UWU","${bundle}")
        }
//        sendIntent.putExtra(EXTRA_DATA, data)
//        Log.d("UWU","${data}")

        mPendingIntent = PendingIntent.getBroadcast(context, 801, sendIntent,PendingIntent.FLAG_UPDATE_CURRENT)

//        mAlarmManager?.setInexactRepeating(AlarmManager.RTC,alarmTimer.timeInMillis, AlarmManager.INTERVAL_FIFTEEN_MINUTES,mPendingIntent)
        mAlarmManager?.set(AlarmManager.RTC,alarmTimer.timeInMillis,mPendingIntent)
//        Toast.makeText(context,"alarm start",Toast.LENGTH_LONG).show()

    }
}