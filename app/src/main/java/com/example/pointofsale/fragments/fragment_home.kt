package com.example.pointofsale.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.core.view.isVisible
import com.example.pointofsale.PREF_NAME
import com.example.pointofsale.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_home.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var interfaceFragment: InterfaceFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        interfaceFragment = activity as InterfaceFragment

        val searchView = view.findViewById<SearchView>(R.id.search_view)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    interfaceFragment.search(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        MobileAds.initialize(context)
        val adRequest = AdRequest.Builder().build()
        val adsview = view.findViewById<AdView>(R.id.adView)
        val btadds = view.findViewById<Button>(R.id.bt_addBanner)

        val adSharePref = context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var removeAd = adSharePref?.getBoolean("ad", false)
        Log.d("admob", "onCreateView: remove = $removeAd")

        if (removeAd!!){
            adsview.isEnabled = false;
            adsview.visibility = View.GONE;
            btadds.visibility = View.GONE
        }else{
            adsview.loadAd(adRequest)
            btadds.visibility = View.GONE
            adsview.adListener = object :AdListener(){
                override fun onAdLoaded() {
                    btadds.visibility = View.VISIBLE
                }
            }
            btadds.setOnClickListener {
                if(adsview.isVisible){
                    adsview.visibility = View.GONE
                    btadds.visibility = View.GONE
                }
            }
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}