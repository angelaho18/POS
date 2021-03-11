@file:Suppress("DEPRECATION")

package com.example.pointofsale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var timeout_splash = 3000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed(
            {
                var homeIntent = Intent(this, login::class.java)
                startActivity(homeIntent)
                Toast.makeText(this, "Welcome to KASIR KU", Toast.LENGTH_LONG).show()
                finish()
            }, timeout_splash)
        timeout_splash = 1000
    }
}