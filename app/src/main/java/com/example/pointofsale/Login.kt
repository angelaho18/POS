package com.example.pointofsale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var user = intent.getParcelableExtra<User>(EXTRA_USER)
        logMail.setText(user?.Nama)

        //intent eksplisit
        reg.setOnClickListener {
            val intent_reg = Intent(this, Register::class.java)
            var user = User(logMail.text.toString())
            intent_reg.putExtra(EXTRA_USER,user)
            startActivity(intent_reg)
        }

        signinbutton.setOnClickListener {
//            val intent_profile = Intent(this, Profile::class.java)
//            var user = User(logMail.text.toString())
//            intent_profile.putExtra(EXTRA_USER, user)
//            startActivity
                if(logMail.length() == 0){
                    Toast.makeText(this, "Please input your Email Address", Toast.LENGTH_SHORT).show()
                } else if(logPass.length() == 0){
                    Toast.makeText(this, "Please input your Password", Toast.LENGTH_SHORT).show()
                } else{
                    val intentAF = Intent(this, activity_fragment::class.java)
                    startActivity(intentAF)
                }
//            val transaction = supportFragmentManager.beginTransaction()
//            val fragmentP = fragment_profile()
//            transaction.replace(R.id.fragmentContainer,fragmentP)
//            transaction.addToBackStack(null)
//            transaction.commit()
        }
        
        forgotPass.setOnClickListener {
            Snackbar.make(it, "Sorry Forgot Password doesn't available yet", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_FULLNAME, logMail.text.toString())
        outState.putString(EXTRA_PASSWORD, logPass.text.toString())
        outState.putBoolean(EXTRA_STATUS, stayLogged.isChecked)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        logMail.setText(savedInstanceState?.getString(EXTRA_FULLNAME, "FULL_NAME"))
        logPass.setText(savedInstanceState?.getString(EXTRA_PASSWORD, "PASSWORD"))
        stayLogged.isChecked = savedInstanceState?.getBoolean(EXTRA_STATUS, false)
    }
}