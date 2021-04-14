package com.example.pointofsale

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.pointofsale.fragments.ActionDismissReceiver
import com.example.pointofsale.model.Reqres
import com.example.pointofsale.model.ReqresItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

class ChannelAndNotifReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val notificationManager = NotificationManagerCompat.from(context)

        val notificationLayout =
            RemoteViews(context.packageName, R.layout.custom_notification)
        val notificationLayoutExpanded =
            RemoteViews(context.packageName, R.layout.custom_notification_expanded)

        var notifs = ArrayList<Notification>()
        val group_key = "Stock's Group"
        val gson = Gson()
        var stock = intent.getStringExtra(EXTRA_STOK)
        var getStock = gson.fromJson(stock, Reqres::class.java)
        var stockDetail = ArrayList<ReqresItem>()
        stockDetail.addAll(getStock)
        Log.d("ALERT-ONE", "Stock Detail: $stockDetail")
//        var stock = intent.getParcelableArrayListExtra<Product>(EXTRA_STOK)
//        val bytes = intent.getByteArrayExtra(EXTRA_DATA)

//        if (stock  != null) stockDetail = stock
//        if (stock == null && bytes != null){
//            var parcel = ParcelableUtil.unmarshall(bytes)
////            var unmarshall = Product.CREATOR.createFromParcel(parcel)
//            var unmarshall: Product = Product(parcel)
//
//            stockDetail.add(unmarshall)
//        }

        if (stockDetail != null) {
            for (i in stockDetail) {
                var description = "${i.storeID} more left"

                notificationLayout.setTextViewText(R.id.title, "${i.title}")
                notificationLayout.setTextViewText(R.id.desc, description)

                notificationLayoutExpanded.setTextViewText(R.id.title, "${i.title}")
                notificationLayoutExpanded.setTextViewText(R.id.desc, description)

                var target = object : Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        notificationLayout.setImageViewBitmap(R.id.pic, bitmap)
                        notificationLayoutExpanded.setImageViewBitmap(R.id.bigPic, bitmap)
                    }

                    override fun onBitmapFailed(
                        e: Exception?,
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
                Picasso.get().load("${i.thumb}").into(target)

                val intent = Intent(context, NotificationReceiver::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                val broadcastIntent =
                    Intent(context, ActionDismissReceiver::class.java).apply {
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
                    Intent(context, AlarmReceiver::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        putExtra(EXTRA_ID, notif_id)
                        var data = ArrayList<ReqresItem>()
//                        data.add(i)
//                        putExtra(EXTRA_DATA, i)
                    }
                val alarmPendingIntent = PendingIntent.getBroadcast(
                    context,
                    notif_id,
                    IntentAlarmReceiver,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

                val newNotif =
                    NotificationCompat.Builder(context, BaseNotification.CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.ic_description)
                        .setColor(Color.BLUE)
                        .setContentTitle("${i.title}")
                        .setContentText("Almost out of Stock ")
                        .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                        .setCustomContentView(notificationLayout)
                        .setCustomBigContentView(notificationLayoutExpanded)
                        .setGroup(group_key)
                        .addAction(R.mipmap.ic_launcher, "Remind me Later", alarmPendingIntent)
                        .addAction(R.mipmap.ic_launcher, "DISMISS", actionIntent)
                        .setOnlyAlertOnce(true)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .build()
                notifs.add(newNotif)

                notificationManager.notify(notif_id, newNotif)
                notif_id++
                count++
            }
        }
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        val buildNotification =
            NotificationCompat.Builder(context, BaseNotification.CHANNEL_1_ID)
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
            notify(BaseNotification.NOTIFICATION_ID, buildNotification)
        }
    }
}