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
import com.example.pointofsale.fragments.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.navigation_button.*

class activity_fragment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        //        untuk tampilan nav
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        val transaction = supportFragmentManager.beginTransaction()
        val fragmentP = fragment_profile()
        transaction.replace(R.id.fragmentContainer,fragmentP)
        transaction.addToBackStack(null)
        transaction.commit()

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.person1 ->{
                    val transaction = this.supportFragmentManager.beginTransaction()
                    val fragmentP = fragment_profile()
                    transaction.replace(R.id.fragmentContainer,fragmentP)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                R.id.list1 ->{
                    val transaction = this.supportFragmentManager.beginTransaction()
                    val fragmentP = fragment_list()
                    transaction.replace(R.id.fragmentContainer,fragmentP)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                R.id.income1 ->{
                    val transaction = this.supportFragmentManager.beginTransaction()
                    val fragmentP = fragment_income()
                    transaction.replace(R.id.fragmentContainer,fragmentP)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                R.id.home1 ->{
                    val transaction = this.supportFragmentManager.beginTransaction()
                    val fragmentP = fragment_home()
                    transaction.replace(R.id.fragmentContainer,fragmentP)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }
            true
        }

        fab.setOnClickListener {
            val transaction = this.supportFragmentManager.beginTransaction()
            val fragmentP = fragment_add()
            transaction.replace(R.id.fragmentContainer,fragmentP)
            transaction.addToBackStack(null)
            transaction.commit()
        }


    }

}