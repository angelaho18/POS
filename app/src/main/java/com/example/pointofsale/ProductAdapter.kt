package com.example.pointofsale

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val items: ArrayList<Product>, private val query: String?) :
    RecyclerView.Adapter<ProductAdapter.ItemHolder>() {
    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val ProductName = view.findViewById<TextView>(R.id.product_name)
        val Quantity = view.findViewById<TextView>(R.id.quantity)
        val Price = view.findViewById<TextView>(R.id.price)
        val row = view.findViewById<LinearLayout>(R.id.row)

        fun changeColor(item: Product, term: String?) {
            if (term !== null) {
                if (item.ProductName.contains(term.toString())) {
                    Log.i("TAG", "$term")
                    Toast.makeText(itemView.context, "hey, you find me", Toast.LENGTH_LONG).show()
                    row.setBackgroundColor(Color.RED)
                    return
                } else {
                    Toast.makeText(
                        itemView.context,
                        "Sorry, we can't find the item",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
        return ItemHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var item = items.get(position)
        holder.ProductName.text = item.ProductName
        holder.Quantity.text = item.Quantity.toString()
        holder.Price.text = item.Price.toString()
        holder.changeColor(item, query)
    }

    override fun getItemCount(): Int = items.size


}

