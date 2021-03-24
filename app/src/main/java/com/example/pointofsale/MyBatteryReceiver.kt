package com.example.pointofsale

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.widget.Toast

class MyBatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val status: Int = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0) ?: -1
        val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL

        val chargePlug: Int = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) ?: -1
        val usbCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
        val acCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_AC

        if (isCharging && acCharge){
            Toast.makeText(context, "AC CHARGING", Toast.LENGTH_SHORT).show()
        } else if (isCharging && usbCharge){
            Toast.makeText(context, "USB CHARGING", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(context, "NOT CHARGING", Toast.LENGTH_SHORT).show()
        }
    }
}