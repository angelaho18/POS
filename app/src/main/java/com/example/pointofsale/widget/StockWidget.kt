package com.example.pointofsale.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import com.example.pointofsale.ActivityFragment
import com.example.pointofsale.EXTRA_NOTIF
import com.example.pointofsale.R


/**
 * Implementation of App Widget functionality.
 */
private const val TAG = "STOCK_WIDGET"
class StockWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        Log.d(TAG, "onUpdate: ")
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        Log.d(TAG, "onEnabled: ")
        // Enter relevant functionality for when the first widget is created

    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.d(TAG, "onDisabled: ")
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.d(TAG, "onReceive: ${intent?.action}")
        val action = intent?.action
        if (ACTION_REFRESH == action){
            Log.d(TAG, "onReceive: refresh")
            Toast.makeText(context, "Sync Product to Aplikasi Kasir Ku", Toast.LENGTH_SHORT).show()
//            val views = RemoteViews(context?.packageName, R.layout.stock_widget)
//            val appWidgetId = intent?.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context!!.packageName, javaClass.name)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)
//            appWidgetManager.updateAppWidget(intent?.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS), views)
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list)
            onUpdate(context, appWidgetManager, appWidgetIds)
        }
    }

    companion object {
        private const val ACTION_REFRESH = "actionRefresh"

        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            Log.d(TAG, "updateAppWidget:")
            val widgetText = context.getString(R.string.appwidget_text)

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.stock_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            val intent = Intent(context, StockWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            views.setRemoteAdapter(R.id.widget_list, intent)

            val fragment = Intent(context, ActivityFragment::class.java)
            fragment.apply {
                putExtra(EXTRA_NOTIF, true)
            }
            val pendingIntent = PendingIntent.getActivity(context,
                0,
                fragment,
                PendingIntent.FLAG_UPDATE_CURRENT)

            val refreshIntent = Intent(context, StockWidget::class.java)
            refreshIntent.action = ACTION_REFRESH
            val refreshPendingIntent = PendingIntent.getBroadcast(context,
                0,
                refreshIntent,
                0)

            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)
            views.setOnClickPendingIntent(R.id.refresh_btn, refreshPendingIntent)

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

