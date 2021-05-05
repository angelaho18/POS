package com.example.pointofsale

import android.content.Context
import android.content.SharedPreferences

class SharePrefHelper (context: Context,FileName:String) {
    val USER_NAME = "NAMA"
    val USER_EMAIL = "EMAIL"

    private var myPreferences: SharedPreferences
    init {
        myPreferences = context.getSharedPreferences(FileName, Context.MODE_PRIVATE)
    }
    //untuk menyimpan data dalam set preference

    //khusus utk fun itu berfisal duplikasi utk menghemat memory

    inline fun SharedPreferences.editMe(operation:(SharedPreferences.Editor)->Unit){
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }
    var nama:String?
        get() = myPreferences.getString(USER_NAME,"NULL")
        set(value){
            myPreferences.editMe {
                it.putString(USER_NAME,value)
            }
        }
    var email:String?
        get() = myPreferences.getString(USER_EMAIL,"NULL")
        set(value){
            myPreferences.editMe {
                it.putString(USER_EMAIL,value)
            }
        }
    fun  clearValue(){
        myPreferences.editMe {
            it.clear()
        }
    }
}