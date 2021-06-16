package com.example.pointofsale.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import com.example.pointofsale.R
import com.example.pointofsale.fragments.fragment_income
import com.example.pointofsale.fragments.fragment_income.Companion.getContacts

/**
 * Implementation of App Widget functionality.
 */
private const val TAG = "CONTACT_WIDGET"
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
        Log.d(TAG, "onUpdate: ")
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        Log.d(TAG, "onEnabled: ")
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.d(TAG, "onDisabled: ")
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    Log.d(TAG, "updateAppWidget: ")
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.contact_widget)

    val intent = Intent(context, ContactWidgetService::class.java)
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

    var cursor = getContacts(context)?.count.toString()

    views.setRemoteAdapter(R.id.gridview, intent)
    views.setTextViewText(R.id.contact_count, cursor)

    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.gridview);
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}