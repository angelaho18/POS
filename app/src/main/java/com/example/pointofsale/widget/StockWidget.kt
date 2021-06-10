package com.example.pointofsale.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.example.pointofsale.ActivityFragment
import com.example.pointofsale.EXTRA_NOTIF
import com.example.pointofsale.R


/**
 * Implementation of App Widget functionality.
 */
class StockWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created

    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
    }

    companion object {
        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val widgetText = context.getString(R.string.appwidget_text)

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.stock_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            val intent = Intent(context, StockWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            views.setRemoteAdapter(R.id.widget_list, intent)
//            views.setEmptyView(R.id.widget_list, )

            val fragment = Intent(context, ActivityFragment::class.java)
            fragment.apply {
                putExtra(EXTRA_NOTIF, true)
            }
            val pendingIntent = PendingIntent.getActivity(context,
                0,
                fragment,
                PendingIntent.FLAG_UPDATE_CURRENT)

            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

