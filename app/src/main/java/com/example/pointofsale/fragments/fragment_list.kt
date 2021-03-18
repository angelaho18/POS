package com.example.pointofsale.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pointofsale.Product
import com.example.pointofsale.ProductAdapter
import com.example.pointofsale.R
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.fragment_list.*

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
        Product("Chitato","https://i.ibb.co/dBCHzXQ/paris.jpg", 3,5000),
        Product("Lays","https://i.ibb.co/dBCHzXQ/paris.jpg",5,6500),
        Product("Paddle Pop","https://i.ibb.co/dBCHzXQ/paris.jpg",7,5000),
        Product("Piattos","https://i.ibb.co/dBCHzXQ/paris.jpg",9,1000),
        Product("Oreo","https://i.ibb.co/dBCHzXQ/paris.jpg",10,2000),
        Product("Cheetos","https://i.ibb.co/dBCHzXQ/paris.jpg",3,7000)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    var query: String? = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list, container, false)
        ShimmerView = view.findViewById(R.id.shimmerFrameLayout)
        val searchView = view.findViewById<SearchView>(R.id.search_view)
        query = arguments?.getString("query")
        val input = view.findViewById<SearchView>(R.id.input)
        input.setQuery(query, false)

        input.clearFocus()


        Handler(Looper.getMainLooper()).postDelayed({
            ShimmerView.stopShimmer()
            ShimmerView.visibility = View.GONE
        },3000)

        val recyclerView = view.findViewById<RecyclerView>(R.id.productRecyclerView)
        productAdapter = ProductAdapter(Stock, query)
        recyclerView.adapter = productAdapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        val row = view.findViewById<LinearLayout>(R.id.linear)

        return view
    }

    companion object {
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