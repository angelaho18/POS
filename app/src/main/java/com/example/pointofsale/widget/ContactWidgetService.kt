package com.example.pointofsale.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.pointofsale.R
import com.example.pointofsale.fragments.fragment_income

private const val TAG = "CONTACT_WIDGET"
class ContactWidgetService: RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return ContactRemoteViewsFactory(this, intent)
    }
}

internal class ContactRemoteViewsFactory(private val context: Context, intent: Intent?) : RemoteViewsService.RemoteViewsFactory {
    private var list = emptyList<String>()
    private var cursor: Cursor? = null

    override fun onCreate() {
        Log.d(TAG, "onCreate: ")
    }

    override fun onDataSetChanged() {
        if (cursor != null) {
            cursor?.close();
        }
        cursor = readContacts()
    }

    override fun onDestroy() {
        if (cursor != null) {
            cursor?.close();
        }
    }

    override fun getCount(): Int {
        var count = if (cursor == null) 0 else cursor!!.count
        Log.d(TAG, "getCount: $count")
        return count
    }

    override fun getViewAt(position: Int): RemoteViews? {
        if(position == AdapterView.INVALID_POSITION || cursor == null || !cursor?.moveToPosition(
                position)!!){
            return null
        }
        val remoteViews = RemoteViews(context.packageName, R.layout.contact_widget_item)
        cursor?.moveToPosition(position)
        val name: String = cursor!!.getString(
            cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)!!)
        val num: String = cursor!!.getString(
            cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)!!)
        remoteViews.setTextViewText(R.id.item_name, name)
        remoteViews.setTextViewText(R.id.item_num, num)
        val view = RemoteViews(context.packageName,R.layout.contact_widget)
        view.setTextViewText(R.id.contact_count,(cursor!!.count).toString())
        Log.i(TAG, "getViewAt: ${cursor!!.count}")
        Log.d(TAG, "getViewAt: name $name num $num")
        return remoteViews
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

    private fun getContacts(): Cursor{
        val uri: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val selection = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?"
        val selectionArgs: Array<String>? = Array(1) { "%Supplier%" }
        val sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC"
        return context.contentResolver.query(uri,
            projection,
            selection,
            selectionArgs,
            sortOrder)!!
    }
    fun readContacts(): Cursor? {
        var rs= context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            fragment_income.cols,
            "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?",
            Array(1) { "%Supplier%" },
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC")
        return rs
    }
}
