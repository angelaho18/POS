package com.example.pointofsale

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.LogoutBut
import kotlinx.android.synthetic.main.navigation_button.*
import java.io.File

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
//        untuk tampilan nav
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        if (isExternalStorageReadable())
            readFileExternal()

//        var user = intent.getParcelableExtra<User>(EXTRA_USER)
//        full_name.setText(user?.Nama)
//        email_address.setText(user?.Email)

        LogoutBut.setOnClickListener {
            val intent_logout = Intent(this, Login::class.java)
            startActivity(intent_logout)
        }
    }

    fun openCamera(view: View) {
        cameraPermission()
        //Intent Implisit Kamera
        var gambar = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (gambar.resolveActivity(packageManager) != null) {
            startActivityForResult(gambar, 181)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //data kembali
        if (requestCode == 181 && resultCode == Activity.RESULT_OK && data != null) {
            var thumbnail = data.extras
            profilePic.setImageBitmap(thumbnail?.get("data") as Bitmap)
        }
    }

    private fun cameraPermission() {
        //cek permission
        var permission = arrayOf(Manifest.permission.CAMERA)
        var checkNeed: ArrayList<String> = ArrayList()
        for (x in permission) {
            if (ContextCompat.checkSelfPermission(this@Profile,
                    x) != PackageManager.PERMISSION_GRANTED
            ) {
                checkNeed.add(x)
            }
        }
        if (!checkNeed.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                checkNeed.toArray(arrayOfNulls(checkNeed.size)),
                18)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            123 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Berhasil membaca data", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "Gagal membaca data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isExternalStorageReadable(): Boolean{
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 123)
        }
        var state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state)
            return true

        return false
    }

    private fun readFileExternal() {
        var myDir = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.toURI())
        full_names.text?.clear()
        email_address.text?.clear()
        var readFile = ""
        var i = 1
        File(myDir, "regisData.txt").forEachLine {
            if(i % 2 == 1) full_names.setText(it)
            if (i % 2 == 0) email_address.setText(it)
            i++
        }
    }
}
