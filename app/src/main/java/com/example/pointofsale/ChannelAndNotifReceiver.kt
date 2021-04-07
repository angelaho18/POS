package com.example.pointofsale

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.pointofsale.fragments.notificationReceiver
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class ChannelAndNotifReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var notificationManager = NotificationManagerCompat.from(context)

        val notificationLayout =
            RemoteViews(context.packageName, R.layout.custom_notification)
        val notificationLayoutExpanded =
            RemoteViews(context.packageName, R.layout.custom_notification_expanded)

        var notifs = ArrayList<Notification>()
        var notif_id = 0
        val group_key = "Stock's Group"
        var stockDetail = intent.getParcelableArrayListExtra<Product>(EXTRA_STOK)
        var stockDATA = intent.getParcelableArrayListExtra<Product>(EXTRA_DATA)
        val bundle = intent.extras
//        var stocks = bundle?.getParcelableArrayList<Product>("data1")

        Log.d("DDT",stocks.toString())
//        Log.d("DDA",stockDATA.toString())

        if (stockDetail != null) {
            for (i in stockDetail) {
                var description = "${i.Quantity} more left"

                notificationLayout.setTextViewText(R.id.title, "${i.ProductName}")
                notificationLayout.setTextViewText(R.id.desc, description)

                notificationLayoutExpanded.setTextViewText(R.id.title, "${i.ProductName}")
                notificationLayoutExpanded.setTextViewText(R.id.desc, description)

                var target = object : Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        notificationLayout.setImageViewBitmap(R.id.pic, bitmap)
                        notificationLayoutExpanded.setImageViewBitmap(R.id.bigPic, bitmap)
                    }

                    override fun onBitmapFailed(
                        e: java.lang.Exception?,
                        errorDrawable: Drawable?
                    ) {
                        notificationLayout.setImageViewResource(R.id.pic, R.drawable.example)
                        notificationLayoutExpanded.setImageViewResource(
                            R.id.bigPic,
                            R.drawable.example
                        )
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    }
                }
                Picasso.get().load("${i.ProductPic}").into(target)

                // go to fragment list
                val intent = Intent(context, NotificationReceiver::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

                val broadcastIntent =
                    Intent(context, notificationReceiver::class.java).apply {
                        action = Intent.ACTION_DELETE
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        putExtra(EXTRA_ID, notif_id)
                    }
                val actionIntent = PendingIntent.getBroadcast(
                    context,
                    notif_id,
                    broadcastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

                val IntentAlarmReceiver =
                    Intent(context, alarmReceiver::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        putExtra(EXTRA_ID, notif_id)
//                        var data_i = ArrayList<Product>()
//                        data_i.add(i)
                        putExtra(EXTRA_DATA, i)
//                        Log.d("iUU","${data_i}")
                    }
                val alarmPendingIntent = PendingIntent.getBroadcast(
                    context,
                    notif_id,
                    IntentAlarmReceiver,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

                val newNotif =
                    NotificationCompat.Builder(context, baseNotification.CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.ic_description)
                        .setColor(Color.BLUE)
                        .setContentTitle("${i.ProductName}")
                        .setContentText("Almost out of Stock ")
                        .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                        .setCustomContentView(notificationLayout)
                        .setCustomBigContentView(notificationLayoutExpanded)
                        .setGroup(group_key)
                        .addAction(R.mipmap.ic_launcher, "DISMISS", actionIntent)
                        .addAction(R.mipmap.ic_launcher, "Remind later", alarmPendingIntent)
                        .setOnlyAlertOnce(true)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build()
                notifs.add(newNotif)

                NotificationManagerCompat.from(context).notify(notif_id, newNotif)
                notif_id++
            }
        }

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)


        val buildNotification =
            NotificationCompat.Builder(context, baseNotification.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.kasirku_logo_blue)
                .setStyle(
                    NotificationCompat.InboxStyle()
                        .setSummaryText("${notifs.count()} new notifications")
                )
                .setColor(ContextCompat.getColor(context!!, R.color.blue))
                .setGroup(group_key)
                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_ALL)
                .setGroupSummary(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setNumber(notifs.count())
                .setContentIntent(pendingIntent)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .build()

        notificationManager.apply {
            notify(baseNotification.NOTIFICATION_ID, buildNotification)
        }


    }
}