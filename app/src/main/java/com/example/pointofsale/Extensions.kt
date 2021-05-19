package com.example.pointofsale

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.Patterns
import android.widget.Toast
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.startActivity
import androidx.core.content.ContextCompat

fun CharSequence?.isEmailValid() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

//fun isExternalStorageReadable(activity: Activity): Boolean{
//    if (ContextCompat.checkSelfPermission(
//            activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//    ){
//        requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 123)
//    }
//
//    var state = Environment.getExternalStorageState()
//    if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state)
//        return true
//
//    return false
//}

