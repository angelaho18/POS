package com.example.pointofsale

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.navigation_button.*

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
//        untuk tampilan nav
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        var user = intent.getParcelableExtra<User>(EXTRA_USER)
        full_name.setText(user?.Nama)
        emailAddress.setText(user?.Email)

        LogoutBut.setOnClickListener {
            val intent_logout = Intent(this,login::class.java)
            startActivity(intent_logout)
        }
    }
    fun openCamera(view: View){
        cameraPermission()
        //Intent Implisit Kamera
        var gambar = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(gambar.resolveActivity(packageManager)!=null){
            startActivityForResult(gambar,181)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //data kembali
        if(requestCode == 181 && resultCode==Activity.RESULT_OK && data !=null){
            var thumbnail = data.extras
            gambarProfile.setImageBitmap(thumbnail?.get("data")as Bitmap)
        }
    }

    private fun cameraPermission() {
        //cek permission
        var permission = arrayOf(Manifest.permission.CAMERA)
        var checkNeed : ArrayList<String> = ArrayList()
        for(x in permission){
            if(ContextCompat.checkSelfPermission(this@Profile,x) != PackageManager.PERMISSION_GRANTED){
                checkNeed.add(x)
            }
        }
        if(!checkNeed.isEmpty()){
            ActivityCompat.requestPermissions(this,checkNeed.toArray(arrayOfNulls(checkNeed.size)),18)
        }
    }

}