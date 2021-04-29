package com.example.pointofsale.presenter

import com.example.pointofsale.model.User
import com.example.pointofsale.view.regisviewInterface

class regisPresenter (internal var regisviewInterface: regisviewInterface):regispresenterInterface{
    override fun regis(fullname: String, email: String, password: String) {
        val user = User(fullname,email,password)
        val regiscode = user.isvalid()
        if(regiscode == 3)
            regisviewInterface.regiserror("Fullname empty")
        else if(regiscode == 0)
            regisviewInterface.regiserror("Email Empty")
        else if(regiscode == 1)
            regisviewInterface.regiserror("email pattern not match")
        else if(regiscode == 2)
            regisviewInterface.regiserror("password must upto 0 character")
        else
            regisviewInterface.regissuccess("login success")
    }

}