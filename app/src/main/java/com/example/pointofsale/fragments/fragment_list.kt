package com.example.pointofsale.fragments

import android.app.AlertDialog
import android.app.Notification
import android.content.Context
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pointofsale.*
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.internal.NavigationMenu
import kotlinx.android.synthetic.main.alert_dialog_stock.view.*
import java.io.InputStream
import java.net.URL

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
    private var Stock: ArrayList<Product> = arrayListOf(
        Product("Chitato", "https://i.ibb.co/dBCHzXQ/paris.jpg", 3, 5000),
        Product("Lays", "https://i.ibb.co/dBCHzXQ/paris.jpg", 5, 6500),
        Product("Paddle Pop", "https://i.ibb.co/dBCHzXQ/paris.jpg", 7, 5000),
        Product("Piattos", "https://i.ibb.co/dBCHzXQ/paris.jpg", 9, 1000),
        Product("Oreo", "https://i.ibb.co/dBCHzXQ/paris.jpg", 10, 2000),
        Product("Cheetos", "https://i.ibb.co/dBCHzXQ/paris.jpg", 3, 7000)
    )

    //    private lateinit var arrayStock: ArrayList<String>
    private lateinit var notificationManager: NotificationManagerCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    private lateinit var dialog: AlertDialog
    private lateinit var service:Intent

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

        val recyclerView = view.findViewById<RecyclerView>(R.id.productRecyclerView)
        productAdapter = ProductAdapter(Stock, query)
        recyclerView.adapter = productAdapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        var arrayStock = ArrayList<String>()
        var stockDetail = ArrayList<Product>()
        for (i in Stock) {
            if (i.Quantity <= 3) {
                arrayStock.add(i.ProductName)
                stockDetail.add(i)
            }
        }

        var hsl = ""
        for (i in arrayStock) {
            if (i == arrayStock.last()) {
                hsl += i
                hsl += ""
            } else {
                hsl += i
                hsl += ", "
            }
        }

        service = Intent(context, ServiceStock::class.java)

        Handler().postDelayed({
            if (hsl != null) {
                val view = View.inflate(context, R.layout.alert_dialog_stock, null)
                val builder = AlertDialog.Builder(context)
                builder.setView(view)

                view.listBarang.setText(hsl)

                dialog = builder.create()
                dialog.show()
                requireActivity().startService(service)
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setCancelable(false)

                notificationManager = NotificationManagerCompat.from(view.context)
                val title = "Stok Barang"
                val message = "$hsl is almost out of stock"

                val notificationLayout =
                    RemoteViews(activity?.packageName, R.layout.custom_notification)
                val notificationLayoutExpanded =
                    RemoteViews(activity?.packageName, R.layout.custom_notification_expanded)

                var notifs = ArrayList<Notification>()
                var notif_id = 0
                val group_key = "Stock's Group"
                for (i in stockDetail) {
                    var description = "${i.Quantity} more left"

                    notificationLayout.setTextViewText(R.id.title, "${i.ProductName}")
                    notificationLayout.setTextViewText(R.id.desc, description)

                    notificationLayoutExpanded.setTextViewText(R.id.title, "${i.ProductName}")
                    notificationLayoutExpanded.setTextViewText(R.id.desc, description)

                    var inStream: InputStream
                    try {
                        var url: URL = URL("${i.ProductPic}")
                        var conn = url.openConnection()
                        conn.doInput = true
                        conn.connect()
                        inStream = conn.getInputStream()
                        var bitmap = BitmapFactory.decodeStream(inStream)

                        notificationLayout.setImageViewBitmap(R.id.pic, bitmap)
                        notificationLayoutExpanded.setImageViewBitmap(R.id.bigPic, bitmap)
                    } catch (e: Exception) {
                        notificationLayout.setImageViewResource(R.id.pic, R.drawable.example)
                        notificationLayoutExpanded.setImageViewResource(R.id.bigPic, R.drawable.example)
                    }

                    val intent = Intent(context, activity_fragment::class.java).apply{
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        passData("notif")
                    }
                    val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

                    val broadcastIntent = Intent(view.context,notificationReceiver::class.java)
                    val actionIntent = PendingIntent.getBroadcast(view.context, 0,broadcastIntent,PendingIntent
                        .FLAG_UPDATE_CURRENT)

                    val newNotif =
                        NotificationCompat.Builder(view.context, baseNotification.CHANNEL_1_ID)
                            .setSmallIcon(R.drawable.ic_description)
                            .setColor(Color.BLUE)
                            .setContentTitle("${i.ProductName}")
                            .setContentText("Almost out of Stock ")
                            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                            .setCustomContentView(notificationLayout)
                            .setCustomBigContentView(notificationLayoutExpanded)
                            .setGroup(group_key)
                            .addAction(R.mipmap.ic_launcher,"Dismiss", actionIntent)
                            .setOnlyAlertOnce(true)
                            .setContentIntent(pendingIntent)
                            .build()
                    notifs.add(newNotif)

                    notificationManager.notify(notif_id, newNotif)
                    notif_id++
                }

                val buildNotification =
                    NotificationCompat.Builder(view.context, baseNotification.CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.kasirku_logo_blue)
                        .setStyle(
                            NotificationCompat.InboxStyle()
                                .setSummaryText("${notifs.count()} new notifications")
                        )
                        .setColor(ContextCompat.getColor(context!!, R.color.blue))
                        .setGroup(group_key)
                        .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_ALL)
                        .setGroupSummary(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setNumber(notifs.count())
//                        .setCategory(NotificationCompat.CATEGORY_REMINDER)
//                        .build()

//                val notification = buildNotification.build()

                notificationManager.apply {
//                    for (notif in notifs){
//                        notify(baseNotification.NOTIFICATION_ID, notif.build())
//                    }
                    notify(baseNotification.NOTIFICATION_ID, buildNotification.build())
                }

                view.alertStockBut.setOnClickListener {
                    requireActivity().stopService(service)
                    dialog.dismiss()
                }
            }
        }, 4000L)

        return view
    }

    lateinit var dataPasser: InterfaceFragment

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as InterfaceFragment
    }

    fun passData(data: String){
        dataPasser.onDataPass(data)
    }

    companion object {
        fun dismiss(){
            fragment_list().apply {
                alertStockBut.setOnClickListener {
                    requireActivity().stopService(service)
                    dialog.dismiss()
                }
            }

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