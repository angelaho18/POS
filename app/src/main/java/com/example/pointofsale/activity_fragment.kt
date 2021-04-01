package com.example.pointofsale

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pointofsale.fragments.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.navigation_button.*

class activity_fragment : AppCompatActivity(), InterfaceFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

//        Handler().postDelayed({
//            unregisterReceiver(BatteryReceiver)
//        }, 10000L)

        //untuk tampilan bottom nav bar
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        newTransaction(fragment_home())

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home1 -> newTransaction(fragment_home())
                R.id.income1 -> newTransaction(fragment_income())
                R.id.list1 -> newTransaction(fragment_list())
                R.id.person1 -> newTransaction(fragment_profile())
            }
            true
        }

        fab.setOnClickListener {
            newTransaction(fragment_add())
        }

        var openNotif = intent.getBooleanExtra(EXTRA_NOTIF, false)
        if(openNotif) newTransaction(fragment_list())
    }

    override fun search(searchTerm: String) {
        val bundle = Bundle()
        bundle.putString("query", searchTerm)
        val transaction = this.supportFragmentManager.beginTransaction()
        val listFragment = fragment_list()
        listFragment.arguments = bundle
        transaction.replace(R.id.fragmentContainer, listFragment).addToBackStack(null).commit()
    }

    override fun onDataPass(data: String) {
        Log.d("TAG", "$data")
        if(data.equals("notif")) {
            var intent = Intent(this, activity_fragment::class.java)
            intent.putExtra(EXTRA_NOTIF, true)
            startActivity(intent)
        }
    }


    private fun newTransaction(fragment: Fragment) {
        val transaction = this.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}

