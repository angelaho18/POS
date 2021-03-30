package com.example.pointofsale

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.widget.TextView
import android.widget.Toast
import com.example.pointofsale.fragments.fragment_list

open class MyBatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val status: Int = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0) ?: -1
        val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL

        val isFull: Boolean = status == BatteryManager.BATTERY_STATUS_FULL

        val chargePlug: Int = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) ?: -1
        val usbCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
        val acCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_AC

        var batteryPct = intent.let{
            var level = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            var scale = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            level * 100 / scale
        }

//        var batteryPct = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)

//        if (isCharging && acCharge){
//            if (isFull)
//                Toast.makeText(context, "AC CHARGING, Battery is already FULL", Toast.LENGTH_SHORT).show()
//            else
//                Toast.makeText(context, "AC CHARGING, Battery level: $batteryPct%", Toast.LENGTH_SHORT).show()
//        } else if (isCharging && usbCharge){
//            if (isFull)
//                Toast.makeText(context, "AC CHARGING, Battery is already FULL", Toast.LENGTH_SHORT).show()
//            else
//                Toast.makeText(context, "USB CHARGING, Battery level: $batteryPct%", Toast.LENGTH_SHORT).show()
//        } else{
//            Toast.makeText(context, "NOT CHARGING", Toast.LENGTH_SHORT).show()
//        }


    }

    fun cetak(){
//        status.text = "hi"
    }

}