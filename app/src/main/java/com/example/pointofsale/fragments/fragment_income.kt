package com.example.pointofsale.fragments

import android.R.attr.name
import android.content.ContentProviderOperation
import android.content.ContentValues.TAG
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.provider.ContactsContract.PhoneLookup
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.pointofsale.R
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
//import com.example.pointofsale.widget.ContactSharePref
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
//    private lateinit var contactSharePref: ContactSharePref

    private var mRewardedAd: RewardedAd? = null
    private final var TAG = "Income"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_income, container, false)

//        contactSharePref = ContactSharePref(view.context)

        Handler().postDelayed({
            readContacts()
        }, 10L)

        val lv = view.findViewById<ListView>(R.id.listview1)
        lv.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var txt = getContactName(position)
            var phone = getContactNum(position)
            Log.d(TAG, "onCreateView: listview $txt num $phone")
        }
        lv.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
            context?.let {
                AlertDialog.Builder(it)
                    .setTitle("Delete")
                    .setMessage("Anda Yakin mau HAPUS?")
                    .setPositiveButton("Ya, Sangat") { dialogInterface, i ->
                        var name = getContactName(position)
                        var phone = getContactNum(position)
//                        Toast.makeText(context, "Yakin 100%", Toast.LENGTH_LONG).show()
                        removeContacts(name, phone)
                        readContacts()
                    }
                    .setNegativeButton("Tidak") { dialogInterface, i ->
//                        Toast.makeText(context, "Uups Salah Pencet", Toast.LENGTH_LONG).show()
                    }
                    .show()
            }
           
            true
        }

        val fab = view.findViewById<FloatingActionButton>(R.id.fabContact)
        fab.setOnClickListener {
            val views = layoutInflater.inflate(R.layout.form_contact, null, false)

            var dialog = AlertDialog.Builder(view.context)
                .setView(views)
                .create()

            dialog.show()

            val bt_add =  views.findViewById<Button>(R.id.bt_add)
            val nama_supplier =  views.findViewById<EditText>(R.id.nama_supplier)
            val nomor_supplier =  views.findViewById<EditText>(R.id.nomor_supplier)
            val email_supplier = views.findViewById<EditText>(R.id.email_supplier)

            bt_add.setOnClickListener {
                addContact(nama_supplier.text.toString(),
                    "${nomor_supplier.text}",
                    "${email_supplier.text}")
                readContacts()
                dialog.dismiss()
            }
        }

        MobileAds.initialize(context) {}

        var adRequest = AdRequest.Builder().build()

        RewardedAd.load(context,"ca-app-pub-6092911080978850~3655446770", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("rewarded", adError?.message)
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                mRewardedAd = rewardedAd
            }
        })

        mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                // Called when ad is dismissed.
                // Don't set the ad reference to null to avoid showing the ad a second time.
                mRewardedAd = null
            }
        }

        if (mRewardedAd != null) {
            mRewardedAd?.show(activity, OnUserEarnedRewardListener() {
                Log.d(TAG, "User earned the reward.")
            })
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
        }

        return view
    }

    private fun getContactName(pos: Int): String {
        var contactsCursor = getContacts()
        contactsCursor?.moveToPosition(pos)
        val name: String = contactsCursor!!.getString(
            contactsCursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
        return name
    }

    private fun getContactNum(pos: Int): String {
        var contactsCursor = getContacts()
        contactsCursor?.moveToPosition(pos)
        val num: String = contactsCursor!!.getString(
            contactsCursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
        return num
    }

    fun readContacts(){
        var fromlst = listOf<String>(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ).toTypedArray()
        var tolst = intArrayOf(android.R.id.text1, android.R.id.text2)
        var rs= requireActivity().contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            cols,
            "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?",
            Array(1) { "%Supplier%" },
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC")
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
                        Array(1) { "%Supplier% %$p0%" },
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                adapter.changeCursor(rs)
                return false
            }
        })
    }

    private fun getContacts(): Cursor? {
        val uri: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val selection = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?"
        val selectionArgs: Array<String>? = Array(1) { "%Supplier%" }
        val sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC"
        return requireActivity().contentResolver.query(uri,
            projection,
            selection,
            selectionArgs,
            sortOrder)
    }

    private fun removeContacts(name: String, num: String) {
//        val whereClause =
//            "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY}  =  ? AND ${ContactsContract.CommonDataKinds.Phone.NUMBER} = ?"
//        var selectionArgs = arrayOf(nama, num)
//        requireActivity().contentResolver.delete(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            whereClause,
//            selectionArgs)
        val contactUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(num))
        val cursor = requireActivity().contentResolver.query(contactUri, null, null, null, null)
        try {
            if (cursor!!.moveToFirst()) {
                do {
                    if (cursor?.getString(cursor?.getColumnIndex(PhoneLookup.DISPLAY_NAME))
                            .equals(name, ignoreCase = true)
                    ) {
                        val lookupKey =
                            cursor?.getString(cursor?.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY))
                        val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI,
                            lookupKey)
                        requireActivity().contentResolver.delete(uri, null, null)
                    }
                } while (cursor?.moveToNext())
            }
        } catch (e: java.lang.Exception) {
            println(e.stackTrace)
        } finally {
            cursor?.close()
        }
    }

    fun addContact(name: String, phone: String, email: String){
        val cops = ArrayList<ContentProviderOperation>()

        cops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
            .build())

        cops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
            .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, "Supplier $name")
            .build());

        cops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "$phone")
            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
            .build());

        cops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
            .withValue(ContactsContract.CommonDataKinds.Email.DATA, "$email")
            .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
                ContactsContract.CommonDataKinds.Email.TYPE_WORK)
            .build());

        try {
            requireActivity().contentResolver.applyBatch(ContactsContract.AUTHORITY, cops)
        } catch (exception: Exception) {
            Log.i(TAG, "exception.message")
        }
    }
    companion object {
        var cols = listOf<String>(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone._ID
        ).toTypedArray()

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