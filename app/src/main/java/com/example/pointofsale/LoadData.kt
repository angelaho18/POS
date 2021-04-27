package com.example.pointofsale

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.loader.content.AsyncTaskLoader
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.pointofsale.fragments.fragment_list
import com.example.pointofsale.model.Reqres
import com.example.pointofsale.model.ReqresItem

class LoadData(context: Context?) : AsyncTaskLoader<MutableList<ReqresItem>>(context!!) {

    override fun loadInBackground(): MutableList<ReqresItem>? {
        Log.d("RESPONSE", "loadInBackground: HELLOOOO")
        AndroidNetworking.initialize(context)
        AndroidNetworking.get("https://www.cheapshark.com/api/1.0/deals")
            .build()
            .getAsObject(Reqres::class.java, object : ParsedRequestListener<Reqres> {
                override fun onResponse(response: Reqres) {
                    Data.addAll(response)
                    Log.d("RESPONSE", "onResponse: $Data")
                    fragment_list.productAdapter.notifyDataSetChanged()
                    Log.d(ContentValues.TAG, "onResponse: $response")
                }

                override fun onError(anError: ANError?) {
                    Log.d(ContentValues.TAG, "onError: ERROR")
                    Toast.makeText(
                        context,
                        "Jaringan anda sedang tidak stabil",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        return Data
    }

//    override fun deliverResult(data: MutableList<ReqresItem>?) {
//        super.deliverResult(data)
//    }

    companion object{
        var Data = mutableListOf<ReqresItem>()
    }
}