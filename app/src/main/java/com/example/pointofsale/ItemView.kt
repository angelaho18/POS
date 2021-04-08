package com.example.pointofsale

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header

class ItemView: JobService() {
    override fun onStartJob(params: JobParameters?): Boolean {
        Log.w("Job", "Mulai")
        getItemList(params)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.w("Job", "Berhenti")
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
                var result = responseBody?.toString(charset) ?: "Kosong"
                Log.w("Hasil", result)
                jobFinished(jobParameters, false)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                jobFinished(jobParameters, true)
            }
        }
        client.get(url, handler)
    }
}