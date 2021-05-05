package com.example.pointofsale.fragments

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.os.BatteryManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.FileProvider
import com.example.pointofsale.R
import com.example.pointofsale.ActivityFragment
import com.example.pointofsale.SharePrefHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.navigation_button.*
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val REQUEST_CODE = 18

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_profile : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val prefFileName = "MyFilepref1"
    private var BatteryReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
            val status: Int = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                    || status == BatteryManager.BATTERY_STATUS_FULL
            
            val chargePlug: Int = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
            val usbCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
            val acCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_AC

            val batteryPct = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10

            if (isCharging && acCharge) {
                print("AC CHARGING", batteryPct, temperature)
            } else if (isCharging && usbCharge) {
                print("USB CHARGING", batteryPct, temperature)
            } else {
                print("NOT CHARGING", batteryPct, temperature)
            }
        }

        private fun print(state: String, percentage: Int, temperature: Int) {
            status.text = state
            percent.text = "$percentage%"
            temp.text = "$temperature \u00B0C"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val image = data?.extras?.get("data") as Bitmap
            gambarProfile.setImageBitmap(image)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val btnPic = view.findViewById<Button>(R.id.buttonPic)
        btnPic.setOnClickListener {
            val takePicIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePicIntent, REQUEST_CODE)
        }

        val outBtn = view.findViewById<Button>(R.id.LogoutBut)
        outBtn.setOnClickListener{
            val intentIn = Intent(context, ActivityFragment::class.java)
            startActivity(intentIn)
        }

        val save = view.findViewById<Button>(R.id.bt_save)
        val reset = view.findViewById<Button>(R.id.bt_reset)
        val edit = view.findViewById<Button>(R.id.bt_edit)
        save.setOnClickListener(this)
        reset.setOnClickListener(this)
        edit.setOnClickListener(this)
        Log.i("kiii",save.toString())
        Log.i("kii",edit.toString())
        Log.i("ki",reset.toString())

        return view
    }

    override fun onResume() {
        super.onResume()
        var filterBattery = IntentFilter()
        filterBattery.addAction(Intent.ACTION_BATTERY_CHANGED)
        requireActivity().registerReceiver(BatteryReceiver, filterBattery)
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(BatteryReceiver)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(p0: View?) {
        var mySharePrefHelper = SharePrefHelper(view!!.context,prefFileName)
        Log.i("kiiiiiii", mySharePrefHelper.toString())

        val fullname = view!!.findViewById<TextInputEditText>(R.id.full_name)
        val mail = view!!.findViewById<TextInputEditText>(R.id.emailAddress)
        Log.d("TAG", "onClick: $p0")
        Log.i("kiiiiiiim", fullname.toString())
        Log.i("kiiiiiiimm", mail.toString())

        when(p0?.id){
            R.id.bt_save ->{
                mySharePrefHelper.nama = fullname?.text.toString()
                mySharePrefHelper.email = mail?.text.toString()
                Log.i("kiiiiiiifullname", mySharePrefHelper.nama.toString())
                Log.i("kiiiiiiimail", mySharePrefHelper.email.toString())
                Toast.makeText(context,"Data tersimpan",Toast.LENGTH_LONG).show()
                fullname?.text?.clear()
                mail?.text?.clear()
            }
            R.id.bt_reset->{
                mySharePrefHelper.clearValue()
                fullname?.text?.clear()
                Log.i("kiiiiiiifullname", mySharePrefHelper.nama.toString())
                Log.i("kiiiiiiimail", mySharePrefHelper.email.toString())
                Toast.makeText(context,"Data reset",Toast.LENGTH_LONG).show()
            }
            R.id.bt_edit->{
                fullname?.setText(mySharePrefHelper.nama)
                mail?.setText(mySharePrefHelper.email)
                Log.i("kiiiiiiifullname", mySharePrefHelper.nama.toString())
                Log.i("kiiiiiiimail", mySharePrefHelper.email.toString())
                Toast.makeText(context,"Data read",Toast.LENGTH_LONG).show()
            }
        }

//        save.setOnClickListener {
//            mySharePrefHelper.nama = fullname.text.toString()
//            mySharePrefHelper.nama = mail.text.toString()
//            Toast.makeText(view.context,"Data tersimpan",Toast.LENGTH_LONG).show()
//            fullname.text.clear()
//            mail.text.clear()
//        }
//        val reset = view.findViewById<Button>(R.id.bt_reset)
//        reset.setOnClickListener {
//            mySharePrefHelper.clearValue()
//        }
//        val edit = view.findViewById<Button>(R.id.bt_edit)
//        edit.setOnClickListener {
//            fullname.setText(mySharePrefHelper.nama)
//            mail.setText(mySharePrefHelper.email)
//        }
    }


}