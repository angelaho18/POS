package com.example.pointofsale.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pointofsale.Product
import com.example.pointofsale.ProductAdapter
import com.example.pointofsale.R

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
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var Stock: ArrayList<Product> = arrayListOf(
        Product("Chitato"),
        Product("Lays"),
        Product("Paddle Pop"),
        Product("Piattos"),
        Product("Oreo"),
        Product("Cheetos")
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

        val searchView = view.findViewById<SearchView>(R.id.search_view)
        query = arguments?.getString("query")
        val input = view.findViewById<TextView>(R.id.input)
        input.text = query

        val recyclerView = view.findViewById<RecyclerView>(R.id.productRecyclerView)
        productAdapter = ProductAdapter(Stock, query)
        recyclerView.adapter = productAdapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        val row = view.findViewById<LinearLayout>(R.id.linear)

//        fun changeColor(item: Product){
//            if(query !== null){
//                for(item in Stock) {
//                    if (item.ProductName.contains(query.toString())) {
//                        Log.i("TAG", "$query")
//                        Toast.makeText(view.context, "hey, you find me", Toast.LENGTH_LONG).show()
////                row.setBackgroundColor(Color.RED)
//                        return view
//                    } else {
//                        Toast.makeText(view.context, "Sorry, we can't find the item", Toast.LENGTH_LONG)
//                            .show()
//                    }
//                }
//            }
////        Toast.makeText(view.context, "Sorry, we can't find the item", Toast.LENGTH_LONG)
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