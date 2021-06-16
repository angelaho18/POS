package com.example.pointofsale.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import com.example.pointofsale.R

/**
 * Implementation of App Widget functionality.
 */
private const val TAG = "blabla"
class ContactWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
        Log.i(TAG, "onUpdate:")
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        Log.i(TAG, "onEnabled: ")
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.i(TAG, "onDisabled: ")
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.contact_widget)

    val intent = Intent(context, ContactWidgetService::class.java)
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

    views.setRemoteAdapter(R.id.gridview, intent)

    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.gridview);
    // Instruct the widget manager to update the widget
    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.gridview)
    appWidgetManager.updateAppWidget(appWidgetId, views)
    Log.i(TAG, "updateAppWidget: ")
}