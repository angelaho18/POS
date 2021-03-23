package com.example.pointofsale

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class ServiceStock : Service() {
    // untuk komunikasi data service ketika diaktifkan dari komponen lain
    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, " service start", Toast.LENGTH_LONG).show()
        Thread(Runnable {
            for(i in 0..10)
                try{
                    Thread.sleep(2000L)
                }catch (e:Exception){

                }
            stopSelf()
        }).start()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, " service done", Toast.LENGTH_LONG).show()
    }
}