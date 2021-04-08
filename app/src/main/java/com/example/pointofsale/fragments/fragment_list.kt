package com.example.pointofsale.fragments

import android.app.AlertDialog
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.pointofsale.*
import com.example.pointofsale.model.Reqres
import com.example.pointofsale.model.ReqresItem
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.alert_dialog_stock.view.*
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
class fragment_list : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var productAdapter: ProductAdapter
    private lateinit var ShimmerView: ShimmerFrameLayout
    private val JobId = 119


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

        startJob()

        AndroidNetworking.initialize(context)
        AndroidNetworking.get("https://www.cheapshark.com/api/1.0/deals")
            .build()
            .getAsObject(Reqres::class.java, object : ParsedRequestListener<Reqres> {
                override fun onResponse(response: Reqres) {
                    Data.addAll(response)
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(context, "Jaringan anda sedang tidak stabil", Toast.LENGTH_SHORT).show()
                }

            })

        val recyclerView = view.findViewById<RecyclerView>(R.id.productRecyclerView)
        productAdapter = ProductAdapter(Data, query)
        recyclerView.adapter = productAdapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

//        var arrayStock = ArrayList<String>()
//        var stockDetail = ArrayList<ReqresItem>()
//        for (i in Data) {
//            if (i.storeID.toInt() <= 3) {
//                arrayStock.add(i.title)
//                stockDetail.add(i)
//            }
//        }
//
//        var hsl = ""
//        for (i in arrayStock) {
//            if (i == arrayStock.last()) {
//                hsl += i
//                hsl += ""
//            } else {
//                hsl += i
//                hsl += ", "
//            }
//        }

        service = Intent(context, ServiceStock::class.java)

//        Handler().postDelayed({
//            if (hsl != null) {
//                val views = View.inflate(context, R.layout.alert_dialog_stock, null)
//                val builder = AlertDialog.Builder(context)
//                builder.setView(views)
//
//                views.listBarang.setText(hsl)
//
//                dialog = builder.create()
//                if (startService) {
//                    dialog.show()
//                    requireActivity().startService(service)
//                }
//                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//                dialog.setCancelable(false)
//
//                var notifIntent = Intent(view.context, ChannelAndNotifReceiver::class.java)
//                notifIntent.putExtra(EXTRA_STOK, stockDetail)
//                requireActivity().sendBroadcast(notifIntent)
//
//                var alertStockBut = views.findViewById<Button>(R.id.alertStockBut)
//
//                alertStockBut.setOnClickListener {
//                    requireActivity().stopService(service)
//                    dialog.dismiss()
//                    startService = false
//                }
//            }
//        }, 3000L)

        return view
    }

    private fun startJob(){
        val serviceComponent = ComponentName(requireActivity(), ShowProductList::class.java)
        val jobInfo = JobInfo.Builder(JobId, serviceComponent)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setRequiresCharging(false)
            .setRequiresDeviceIdle(false)
            .setPeriodic(15 * 60 * 1000)
        var JobList = context?.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        JobList.schedule(jobInfo.build())
        Toast.makeText(context, "Job Start", Toast.LENGTH_SHORT).show()
    }

    private fun cancelJob(){
        var JobList = context?.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        JobList.cancel(JobId)
        Toast.makeText(context, "Job Cancel", Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as InterfaceFragment
    }

    companion object {
        lateinit var dataPasser: InterfaceFragment

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
}