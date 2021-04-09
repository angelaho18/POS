package com.example.pointofsale

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.pointofsale.model.Reqres
import com.example.pointofsale.model.ReqresItem
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header

class ItemView: JobService() {
    val TAG = "HASIL"
    lateinit var runnable: Runnable
    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStartJob: START JOB")
        runnable = Runnable() {
            getItemList(params)
            jobFinished(params, true)
        }
        runnable.run()
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStopJob: STOP JOB")
        return true
    }

    private fun getItemList(jobParameters: JobParameters?) {
        var client = AsyncHttpClient()
        var url = "https://www.cheapshark.com/api/1.0/deals"
        val charset = Charsets.UTF_8
        var handler = object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                Log.d(TAG, "onSuccess: $headers")
                var result = responseBody?.toString(charset) ?: "Kosong"
                Log.d(TAG, "onSuccess: $result")
                AndroidNetworking.initialize(applicationContext)
                AndroidNetworking.get("https://www.cheapshark.com/api/1.0/deals")
                    .build()
                    .getAsObject(Reqres::class.java, object : ParsedRequestListener<Reqres> {
                        override fun onResponse(response: Reqres) {
                            Data.addAll(response)
                            Log.d(TAG, "onResponse: $response")
                        }

                        override fun onError(anError: ANError?) {
                            Log.d(TAG, "onError: ERROR BEB")
                            Toast.makeText(
                                applicationContext,
                                "Jaringan anda sedang tidak stabil",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    })
                jobFinished(jobParameters, false)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d(TAG, "Gagalll")
                Toast.makeText(applicationContext, "Fail to Load", Toast.LENGTH_SHORT).show()
                jobFinished(jobParameters, true)
            }
        }
        client.get(url, handler)
    }

    companion object{
        var Data: MutableList<ReqresItem> = mutableListOf()
    }
}