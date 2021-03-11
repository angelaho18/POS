package com.example.pointofsale

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //intent eksplisit
        reg.setOnClickListener {
            val intent_reg = Intent(this, register::class.java)

            startActivity(intent_reg)
        }

        signinbutton.setOnClickListener {
            val intent_reg = Intent(this, Profile::class.java)
            var user = User(logFullName.text.toString())
            intent_reg.putExtra(EXTRA_USER, user)

            startActivity(intent_reg)
        }

        forgotPass.setOnClickListener {
            Snackbar.make(it, "Sorry Forgot Password doesn't available yet", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_FULLNAME, logFullName.text.toString())
        outState.putString(EXTRA_PASSWORD, logPass.text.toString())
        outState.putBoolean(EXTRA_STATUS, stayLogged.isChecked)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        logFullName.setText(savedInstanceState?.getString(EXTRA_FULLNAME, "FULL_NAME"))
        logPass.setText(savedInstanceState?.getString(EXTRA_PASSWORD, "PASSWORD"))
        stayLogged.isChecked = savedInstanceState?.getBoolean(EXTRA_STATUS, false)
    }
}