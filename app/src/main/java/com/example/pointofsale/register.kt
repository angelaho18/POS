package com.example.pointofsale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //intent eksplisit
        log.setOnClickListener {
            val intent_login = Intent(this,login::class.java)
            var user = User("Ani","Ani@gmail.com")
            intent_login.putExtra(EXTRA_USER,user)
            startActivity(intent_login)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_FULLNAME, fullName.text.toString())
        outState.putString(EXTRA_EMAIL, emailAddress.text.toString())
        outState.putString(EXTRA_PASSWORD, password.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        fullName.setText(savedInstanceState?.getString(EXTRA_FULLNAME))
        emailAddress.setText(savedInstanceState?.getString(EXTRA_EMAIL))
        password.setText(savedInstanceState?.getString(EXTRA_PASSWORD))
    }
}