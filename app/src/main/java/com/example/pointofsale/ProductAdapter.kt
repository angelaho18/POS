package com.example.pointofsale

import android.content.res.ColorStateList
import android.graphics.Color
import android.icu.text.NumberFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*
import java.util.*
import kotlin.collections.ArrayList

class ProductAdapter(private val items: ArrayList<Product>, private val query: String?) :
    RecyclerView.Adapter<ProductAdapter.ItemHolder>() {
    private var found = false

    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val ProductName = view.findViewById<TextView>(R.id.product_name)
        val Quantity = view.findViewById<TextView>(R.id.quantity)
        val Price = view.findViewById<TextView>(R.id.price)
        val row = view.findViewById<LinearLayout>(R.id.row)

    fun changeColor(item: Product, term: String?): Boolean {
        if (term != null) {
            if (item.ProductName.contains(term.toString())) {
                Toast.makeText(itemView.context, "hey, you find me", Toast.LENGTH_LONG).show()
                row.backgroundTintList = ColorStateList.valueOf(Color.GREEN)
                return true
            } else {
                Toast.makeText(
                    itemView.context,
                    "Sorry, we can't find the item",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        return false
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
        if (!found) found = holder.changeColor(item, query)

        holder.Price.text = rupiah(item.Price)

        Picasso.get().load(item.ProductPic).into(holder.view.GambarProduk)
    }

    override fun getItemCount(): Int = items.size

    fun rupiah(Price: Int): String {
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(Price).toString()
    }

}

