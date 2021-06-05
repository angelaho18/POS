package com.example.pointofsale.fragments

import android.content.ContentProviderOperation
import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import com.example.pointofsale.R
import kotlinx.android.synthetic.main.fragment_income.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_income.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_income : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    var cols = listOf<String>(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID
    ).toTypedArray()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_income, container, false)

        Handler().postDelayed({
            readContacts()
        }, 10L)

        return view
    }

    fun readContacts(){
        var fromlst = listOf<String>(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ).toTypedArray()
        var tolst = intArrayOf(android.R.id.text1, android.R.id.text2)
        var rs= requireActivity().contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            cols,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        var adapter = SimpleCursorAdapter(context,
            android.R.layout.simple_list_item_2,
            rs,
            fromlst,
            tolst,
            0)
        listview1.adapter = adapter
        searchView2.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                var rs =
                    requireActivity().contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        cols,
                        "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?",
                        Array(1) { "%$p0%" },
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                adapter.changeCursor(rs)
                return false
            }
        })
    }

    fun addContact(){
//        val cops = ArrayList<ContentProviderOperation>()
//
//        cops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
//            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, "accountname@gmail.com")
//            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "com.google")
//            .build())
//        cops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//            .withValue(ContactsContract.Data.MIMETYPE,
//                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
//                editTextContactName.getText().toString())
//            .build())
//
//        try {
//            contentResolver.applyBatch(ContactsContract.AUTHORITY, cops)
//        } catch (exception: Exception) {
//            Log.i(TAG, "exception.message")
//        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_income.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_income().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}