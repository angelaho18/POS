package com.example.pointofsale.fragments

import android.app.AlertDialog
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.pointofsale.*
import com.example.pointofsale.model.Reqres
import com.example.pointofsale.model.ReqresItem
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.alert_dialog_stock.view.*
import kotlinx.android.synthetic.main.fragment_list.*
import okhttp3.Response
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_list.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_list : Fragment(), LoaderManager.LoaderCallbacks<MutableList<ReqresItem>> {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var ShimmerView: ShimmerFrameLayout

//    private var Stock: ArrayList<Product> = arrayListOf(
//        Product("Chitato", "https://i.ibb.co/dBCHzXQ/paris.jpg", 3, 5000),
//        Product("Lays", "https://i.ibb.co/dBCHzXQ/paris.jpg", 5, 6500),
//        Product("Paddle Pop", "https://i.ibb.co/dBCHzXQ/paris.jpg", 7, 5000),
//        Product("Piattos", "https://i.ibb.co/dBCHzXQ/paris.jpg", 9, 1000),
//        Product("Oreo", "https://i.ibb.co/dBCHzXQ/paris.jpg", 10, 2000),
//        Product("Cheetos", "https://i.ibb.co/dBCHzXQ/paris.jpg", 3, 7000)
//    )

    private var Data: MutableList<ReqresItem> = mutableListOf()

    private lateinit var notificationManager: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var dialog: AlertDialog
    lateinit var service: Intent

    var JobSchedulerId = 5

    var query: String? = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        ShimmerView = view.findViewById(R.id.shimmerFrameLayout)

        query = arguments?.getString("query")
        val input = view.findViewById<SearchView>(R.id.input)
        input.setQuery(query, false)
        input.clearFocus()

        Handler(Looper.getMainLooper()).postDelayed({
            ShimmerView.stopShimmer()
            ShimmerView.visibility = View.GONE
        }, 3000)

//        if (schedule) {
//            startMyJob()
//            schedule = false
//        }
//
        LoaderManager.getInstance(this).initLoader(1, null, this).forceLoad()

        Data = LoadData.Data
//        Log.d("HASIL", "onCreateView: $Data")

//        val recyclerView = view.findViewById<RecyclerView>(R.id.productRecyclerView)
//        productAdapter = ProductAdapter(LoadData.Data, query)
//        recyclerView.adapter = productAdapter
//        recyclerView.layoutManager = LinearLayoutManager(view.context)

        service = Intent(context, ServiceStock::class.java)

        Handler().postDelayed({
            var arrayStock = ArrayList<String>()
            var stockDetail = ArrayList<ReqresItem>()
            for (i in Data) {
                if (i.storeID.toInt() < 2) {
                    arrayStock.add(i.title)
                    stockDetail.add(i)
                }
                Log.d("STORE-ID", "${i.storeID.toInt()}")
            }

            var hsl = ""
            for (i in arrayStock) {
                if (i == arrayStock.last()) {
                    hsl += i
                    hsl += ""
                } else {
                    hsl += i
                    hsl += "\n"
                }
            }
            if (hsl != null) {
                val views = View.inflate(context, R.layout.alert_dialog_stock, null)
                val builder = AlertDialog.Builder(context)
                builder.setView(views)

                views.listBarang.setText(hsl)

                dialog = builder.create()
                if (startService) {
                    dialog.show()
                    requireActivity().startService(service)
                }
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setCancelable(false)

                var gson = Gson()

                Log.d("ALERT", "Stock Detail: $stockDetail")
                var notifIntent = Intent(view.context, ChannelAndNotifReceiver::class.java)
                notifIntent.putExtra(EXTRA_STOK, gson.toJson(stockDetail))

                if (showNotif){
                    requireActivity().sendBroadcast(notifIntent)
                    showNotif = false
                }

                var alertStockBut = views.findViewById<Button>(R.id.alertStockBut)

                alertStockBut.setOnClickListener {
                    requireActivity().stopService(service)
                    dialog.dismiss()
                    startService = false
                }
            }
        }, 5000L)

        return view
    }

    private fun startMyJob() {
        var serviceComponent = ComponentName(requireActivity(), ItemView::class.java)
        var mJobInfo = JobInfo.Builder(JobSchedulerId, serviceComponent)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setRequiresDeviceIdle(false)
            .setRequiresCharging(false)
            .setMinimumLatency(1)
            .setOverrideDeadline(1)
        //            .setPeriodic(15 * 60 * 1000, 5 * 60 * 1000)

        var JobItem = context?.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        JobItem.schedule(mJobInfo.build())
        Toast.makeText(context, "Job Scheduler Start", Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as InterfaceFragment
    }

    companion object {
        lateinit var dataPasser: InterfaceFragment
        lateinit var productAdapter: ProductAdapter

        fun passData(data: String) {
            dataPasser.onDataPass(data)
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_list.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic

        fun newInstance(param1: String, param2: String) =
            fragment_list().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<MutableList<ReqresItem>> {
        Log.d("RESPONSE", "HAIIIII")
        if(context == null) Log.d("RESPONSE", "SEDIHHH")
        else Log.d("RESPONSE", "BISAAA")
        return LoadData(context)
    }

    override fun onLoadFinished(
        loader: Loader<MutableList<ReqresItem>>,
        data: MutableList<ReqresItem>?
    ) {
//        Data.clear()
        Log.d("RESPONSE", "onLoadFinished: YEEAAHHH")
        if (data != null) {
//            Log.d("HEI", "onLoadFinished: $data")
//            Data.addAll(data)
            productAdapter = ProductAdapter(data, query)
            productRecyclerView.adapter = fragment_list.productAdapter
            productRecyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onLoaderReset(loader: Loader<MutableList<ReqresItem>>) {
        var recyclerView = view?.findViewById<RecyclerView>(R.id.productRecyclerView)
        recyclerView?.adapter?.notifyDataSetChanged()
    }
}

