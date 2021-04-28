package com.example.pointofsale.model
import android.text.TextUtils
import android.util.Patterns

class User (override val fullname:String, override val email:String, override  val password:String):UserInterface {
    override fun isvalid(): Int {
        if(TextUtils.isEmpty(email))
            return 0
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return 1
        else if(password.length<=0)
            return 2
        else if(TextUtils.isEmpty(fullname))
            return 3
        else
            return 4
    }
//    override val isvalid: Boolean
//        get() = (!TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 0 && (!TextUtils.isEmpty(fullname))
}