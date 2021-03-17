package com.example.pointofsale

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val items: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ItemHolder>() {
    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val ProductName = view.findViewById<TextView>(R.id.product_name)
        val Quantity = view.findViewById<TextView>(R.id.quantity)
        val Price = view.findViewById<TextView>(R.id.price)
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
    }

    override fun getItemCount(): Int = items.size

    fun changeColor(){
        var view: View? = null
        val row = view?.findViewById<LinearLayout>(R.id.row)
        row?.setBackgroundColor(Color.RED)
    }
}

