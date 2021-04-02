package com.example.pointofsale

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.widget.Toast
import java.util.*
import kotlin.concurrent.schedule

class ServiceStock : Service() {
    private lateinit var mp: MediaPlayer

    // untuk komunikasi data service ketika diaktifkan dari komponen lain
    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mp = MediaPlayer.create(this, R.raw.music)
        mp.isLooping = true
        mp.setVolume(0.5f, 0.5f)
        mp.start()

        Timer().schedule(25000) {
            stopSelf()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mp.stop()
        startService = false
    }
}