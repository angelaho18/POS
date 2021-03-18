package com.example.pointofsale

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pointofsale.fragments.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.navigation_button.*

class activity_fragment : AppCompatActivity(), InterfaceFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        //untuk tampilan bottom nav bar
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        val transaction = supportFragmentManager.beginTransaction()
        val fragmentP = fragment_home()
        transaction.replace(R.id.fragmentContainer, fragmentP)
        transaction.addToBackStack(null)
        transaction.commit()

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.person1 -> {
                    newTransaction(fragment_profile())
                }
                R.id.list1 -> {
                    newTransaction(fragment_list())
                }
                R.id.income1 -> {
                    newTransaction(fragment_income())
                }
                R.id.home1 -> {
                    newTransaction(fragment_home())
                }
            }
            true
        }

        fab.setOnClickListener {
            newTransaction(fragment_add())
        }
    }

    override fun search(searchTerm: String) {
        val bundle = Bundle()
        bundle.putString("query", searchTerm)
        val transaction = this.supportFragmentManager.beginTransaction()
        val listFragment = fragment_list()
        listFragment.arguments = bundle
        transaction.replace(R.id.fragmentContainer, listFragment).commit()
    }

    private fun newTransaction(fragment: Fragment) {
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragments = fragment
        transaction.replace(R.id.fragmentContainer, fragments)
            .addToBackStack(null)
            .commit()
    }

}

