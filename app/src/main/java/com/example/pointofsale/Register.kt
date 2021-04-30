package com.example.pointofsale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.pointofsale.presenter.regisPresenter
import com.example.pointofsale.presenter.regispresenterInterface
import com.example.pointofsale.view.regisviewInterface
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.emailAddress

class Register : AppCompatActivity(),regisviewInterface {

    internal lateinit var regispresenter:regispresenterInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //init
        regispresenter = regisPresenter(this)

        var user = intent.getParcelableExtra<User>(EXTRA_USER)
        fullName.setText(user?.Nama)

        //intent eksplisit
        log.setOnClickListener {
            val intent_login = Intent(this,Login::class.java)
            var user = User(fullName.text.toString())
            intent_login.putExtra(EXTRA_USER,user)
            startActivity(intent_login)
        }

        signup.setOnClickListener {
//            regispresenter.regis(fullName.text.toString(),emailAddress.text.toString(),password.text.toString())
            if(fullName.length() == 0){
                fullName.error = "Please input your FullName"
            } else if(emailAddress.length() == 0){
                emailAddress.error = "Please input your Email Address"
            } else if(password.length() == 0){
                password.error = "Please input your Password"
            } else{
                if(emailAddress.text.isEmailValid()){
                    val intent_profile = Intent(this, Profile::class.java)
                    var user = User(fullName.text.toString(), emailAddress.text.toString())
                    intent_profile.putExtra(EXTRA_USER,user)
                    startActivity(intent_profile)
                }else{
                    emailAddress.error = "Please Enter Valid Email Address"
                }
            }
        }

        emailAddress.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                emailAddress.error = "Please Enter Valid Email Address"
                if(emailAddress.text.isEmailValid())
                    emailAddress.error = null
            }
        })
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

    override fun regissuccess(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    override fun regiserror(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

//    override fun regisResult(message: String) {
//        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
//    }

}