package com.example.pointofsale.Room

import android.app.Application
import com.example.pointofsale.Room.Product
import com.example.pointofsale.Room.ProductDAO

class ProductRepository (application: Application){
    private val productDao: ProductDAO? = null

    val readAllData: List<Product> = productDao!!.getAllData()
    val db = ProductDBHelper.getInstance(application)

    fun insert(product: Product) {
        db?.runInTransaction {
            productDao?.insertAll(product)
        }
    }

    fun update(product: Product) {
        db?.runInTransaction {
            productDao?.updateData(product)
        }
    }

    fun delete(id: Int){
        db?.runInTransaction {
            productDao?.deleteById(id)
        }
    }

    fun getData(): List<Product>{
        return readAllData
    }


}