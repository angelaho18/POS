package com.example.pointofsale.widget

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.pointofsale.Room.Product

private const val TAG = "CONTACT_WIDGET"
class ContactWidgetService: RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return ContactRemoteViewsFactory(this, intent)
    }
}

internal class ContactRemoteViewsFactory(private val context: Context, intent: Intent?) : RemoteViewsService.RemoteViewsFactory {
    private var list = emptyList<String>()

    override fun onCreate() {
        Log.d(TAG, "onCreate: ")
    }

    override fun onDataSetChanged() {
        
    }

    override fun onDestroy() {
        list = emptyList()
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        TODO("Not yet implemented")
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

}
