package com.example.pointofsale

import android.content.res.ColorStateList
import android.graphics.Color
import android.icu.text.NumberFormat
import android.icu.text.Transliterator
import android.provider.ContactsContract
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pointofsale.model.ReqresItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*
import java.io.Console
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class ProductAdapter(private val items: MutableList<ReqresItem>, private val query: String?) :
    RecyclerView.Adapter<ProductAdapter.ItemHolder>() {
    private var found = false
//    var arrayStock = ArrayList<String>()

    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view){
        val ProductName = view.findViewById<TextView>(R.id.product_name)
        val Quantity = view.findViewById<TextView>(R.id.quantity)
        val Price = view.findViewById<TextView>(R.id.price)
        val row = view.findViewById<LinearLayout>(R.id.row)

        fun changeColor(item: ReqresItem, term: String?): Boolean {
            if (term != null) {
                if (item.title.contains(term.toString())) {
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
//
//        fun quantity(item: ReqresItem, arrayStock: ArrayList<String>): ArrayList<String> {
//            if (item.storeID.toInt() <= 3) {
//                arrayStock.add(item.title)
//            }
//            return arrayStock
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
        return ItemHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

//        var item = items.get(position)
//        holder.ProductName.text = item.ProductName
//        holder.Quantity.text = item.Quantity.toString()
//        if (!found) found = holder.changeColor(item, query)

//////        arrayStock = holder.quantity(item, arrayStock)

//        holder.Price.text = rupiah(item.Price)
//
//        Picasso.get().load(item.ProductPic).into(holder.view.GambarProduk)

        val data = items.get(position)

        val angka = data.salePrice
        val getfloat = "f"
        val gabung = angka + getfloat

        holder.ProductName.text = "${data.title}"
        holder.Quantity.text = data.storeID
//        Log.d("WOW", data.salePrice.toInt().toString())
        holder.Price.text = rupiah(gabung.toFloat().toInt() * 14500)
//        holder.Price.text = rupiah(1000)
        Picasso.get().load(data.thumb).into(holder.view.GambarProduk)


    }

    override fun getItemCount(): Int = items.size

    fun rupiah(Price: Int): String {
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(Price).toString()
    }

}

