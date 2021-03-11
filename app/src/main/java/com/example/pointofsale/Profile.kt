package com.example.pointofsale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        var user = intent.getParcelableExtra<User>(EXTRA_USER)
        full_name.setText(user?.Nama)
        emailAddress.setText(user?.Email)

        LogoutBut.setOnClickListener {
            val intent_logout = Intent(this,login::class.java)

            startActivity(intent_logout)
        }
    }
}