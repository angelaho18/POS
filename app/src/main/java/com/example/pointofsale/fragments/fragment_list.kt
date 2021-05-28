package com.example.pointofsale.fragments

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room.databaseBuilder
import com.example.pointofsale.*
import com.facebook.shimmer.ShimmerFrameLayout
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import kotlin.random.Random

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
    private lateinit var ShimmerView: ShimmerFrameLayout
    private lateinit var db: ProductDBHelper
    private val SELECT_PICTURE = 1
    private var selectedImagePath: String? = null
    private lateinit var imageSource: String
    private var inputData: ByteArray? = null

//    private var Stock: ArrayList<Product> = arrayListOf(
//        Product("Chitato", "https://i.ibb.co/dBCHzXQ/paris.jpg", 3, 5000),
//        Product("Lays", "https://i.ibb.co/dBCHzXQ/paris.jpg", 5, 6500),
//        Product("Paddle Pop", "https://i.ibb.co/dBCHzXQ/paris.jpg", 7, 5000),
//        Product("Piattos", "https://i.ibb.co/dBCHzXQ/paris.jpg", 9, 1000),
//        Product("Oreo", "https://i.ibb.co/dBCHzXQ/paris.jpg", 10, 2000),
//        Product("Cheetos", "https://i.ibb.co/dBCHzXQ/paris.jpg", 3, 7000)
//    )

    private var Data: MutableList<Product> = mutableListOf()

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
//        LoaderManager.getInstance(this).initLoader(1, null, this).forceLoad()
//
//        Data = LoadData.Data
//        Log.d("HASIL", "onCreateView: $Data")

//        val recyclerView = view.findViewById<RecyclerView>(R.id.productRecyclerView)
//        productAdapter = ProductAdapter(LoadData.Data, query)
//        recyclerView.adapter = productAdapter
//        recyclerView.layoutManager = LinearLayoutManager(view.context)

//        service = Intent(context, ServiceStock::class.java)
//
//        Handler().postDelayed({
//            var arrayStock = ArrayList<String>()
//            var stockDetail = ArrayList<ReqresItem>()
//            for (i in Data) {
//                if (i.storeID.toInt() < 2) {
//                    arrayStock.add(i.title)
//                    stockDetail.add(i)
//                }
//                Log.d("STORE-ID", "${i.storeID.toInt()}")
//            }
//
//            var hsl = ""
//            for (i in arrayStock) {
//                if (i == arrayStock.last()) {
//                    hsl += i
//                    hsl += ""
//                } else {
//                    hsl += i
//                    hsl += "\n"
//                }
//            }
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
//                var gson = Gson()
//
//                Log.d("ALERT", "Stock Detail: $stockDetail")
//                var notifIntent = Intent(view.context, ChannelAndNotifReceiver::class.java)
//                notifIntent.putExtra(EXTRA_STOK, gson.toJson(stockDetail))
//
//                if (showNotif){
//                    requireActivity().sendBroadcast(notifIntent)
//                    showNotif = false
//                }
//
//                var alertStockBut = views.findViewById<Button>(R.id.alertStockBut)
//
//                alertStockBut.setOnClickListener {
//                    requireActivity().stopService(service)
//                    dialog.dismiss()
//                    startService = false
//                }
//            }
//        }, 5000L)

        db = databaseBuilder(view.context, ProductDBHelper::class.java, "productdbex.db").build()

        val productRecyclerView = view.findViewById<RecyclerView>(R.id.productRecyclerView)

        productAdapter = ProductAdapter(Data, query)
        productRecyclerView.adapter = productAdapter
        productRecyclerView.layoutManager = LinearLayoutManager(context)

        doAsync {
            var data = db.productDao().getAllData()
            Log.d(TAG, "onCreateView: $data")
            uiThread {
                productAdapter.setData(data)
            }
        }


        val addbutton = view.findViewById<Button>(R.id.bt_addStock)

        Log.i("kiiiiadd", addbutton.toString())

        addbutton.setOnClickListener {
            val addView = View.inflate(context, R.layout.layout_pop_up, null)

            val builder = AlertDialog.Builder(context)
            builder.setView(addView)
            dialog = builder.create()

            val browseBtn = addView.findViewById<Button>(R.id.browse_btn)
            browseBtn.setOnClickListener {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), SELECT_PICTURE)
            }

            val confirmBtn = addView.findViewById<Button>(R.id.bt_add_to_conf)
            val cancelBtn = addView.findViewById<Button>(R.id.bt_cancel)
            val name = addView.findViewById<EditText>(R.id.inputName)
            val qty = addView.findViewById<EditText>(R.id.inputQty)
            val price = addView.findViewById<EditText>(R.id.inputPrice)
            dialog.show()
            var hasil = ""
            confirmBtn.setOnClickListener {
                doAsync {
                    try {
                        var productTmp = Product(Random.nextInt())
                        productTmp.ProductName = name.text.toString()
                        productTmp.Quantity = qty.text.toString().toInt()
                        productTmp.Price = price.text.toString().toInt()
//                        productTmp.ProductPic = inputData
                        db.productDao().insertAll(productTmp)

                        var data = db.productDao().getAllData()

                        for (allData in db.productDao().getAllData()) {
                            hasil += "${allData.ProductPic}"
                        }
                        uiThread {
                            productAdapter.setData(data)
//                            getData(db)
                            Log.d("hasilDB", "onCreateView: $hasil")
                            Toast.makeText(view.context, "$hasil", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        uiThread {
                            Log.e("ERROR DB", "$e")
                            Toast.makeText(view.context, "$e", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                dialog.dismiss()
            }
            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }

        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode === RESULT_OK) {
            if (requestCode === SELECT_PICTURE) {
                val selectedImageUri: Uri? = data!!.data
                selectedImagePath = getPath(selectedImageUri)
                Log.d(TAG, "onActivityResult: $selectedImagePath")
                val filename: String? = selectedImagePath?.substring(selectedImagePath?.lastIndexOf(
                    "/")!! + 1)
                val view = View.inflate(context, R.layout.layout_pop_up, null)
//                val vi = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//                val v = vi.inflate(R.layout.layout_pop_up, null)
                val imgFileName = view.findViewById<TextView>(R.id.image_file_name)
                imgFileName.text = filename

                Log.d(TAG, "onActivityResult: view ${imgFileName.text}")
                if(data != null){
                    var inputStream = context?.contentResolver?.openInputStream(selectedImageUri!!)
//                    inputData = getBytes(inputStream!!)
                    var bitmap = BitmapFactory.decodeStream(inputStream)
                    imageSource = ImageBitmapString.BitMapToString(bitmap).toString()
                }
            }
        }
    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray? {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

    fun getPath(uri: Uri?): String? {
        if (uri == null) {
            return null
        }
        var filePath: String? = null
        val fileId: String = DocumentsContract.getDocumentId(uri)
        val id = fileId.split(":".toRegex()).toTypedArray()[1]
        val column = arrayOf(MediaStore.Images.Media.DATA)
        val selector = MediaStore.Images.Media._ID + "=?"
        val cursor = context!!.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            column, selector, arrayOf(id), null)
        if (cursor != null){
            val columnIndex = cursor?.getColumnIndex(column[0])
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex)
            }
            cursor.close()
            return filePath
        }
        Log.d(TAG, "getPath: Uri.Path ${uri.path}")
        return uri.path
    }

//    override fun onStart() {
//        super.onStart()
////        getData(db)
//    }

    private fun resizeImg(img: ByteArray?): ByteArray? {
        var img = img
        while (img?.size!! > 500000) {
            val bitmap: Bitmap = BitmapFactory.decodeByteArray(img, 0, img!!.size)
            val resized = Bitmap.createScaledBitmap(bitmap,
                (bitmap.width * 0.8).toInt(), (bitmap.height * 0.8).toInt(), true)
            val stream = ByteArrayOutputStream()
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream)
            img = stream.toByteArray()
        }
        return img
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

//    override fun onCreateLoader(id: Int, args: Bundle?): Loader<MutableList<ReqresItem>> {
//        Log.d("LOADER", "CREATE LOADER")
//        return LoadData(context)
//    }
//
//    override fun onLoadFinished(
//        loader: Loader<MutableList<ReqresItem>>,
//        data: MutableList<ReqresItem>?
//    ) {
////        Data.clear()
//        Log.d("LOADER", "onLoadFinished: YEEAAHHH")
//        if (data != null) {
////            Log.d("HEI", "onLoadFinished: $data")
////            Data.addAll(data)
//            productAdapter = ProductAdapter(data, query)
//            productRecyclerView.adapter = fragment_list.productAdapter
//            productRecyclerView.layoutManager = LinearLayoutManager(context)
//        }
//    }
//
//    override fun onLoaderReset(loader: Loader<MutableList<ReqresItem>>) {
//        var recyclerView = view?.findViewById<RecyclerView>(R.id.productRecyclerView)
//        recyclerView?.adapter?.notifyDataSetChanged()
//    }
}

