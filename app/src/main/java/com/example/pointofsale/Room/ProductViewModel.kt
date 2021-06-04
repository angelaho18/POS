package com.example.pointofsale.Room

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ProductViewModel (application: Application): AndroidViewModel(application){
    private val repository = ProductRepository(application)
    private val products = repository.getData()

    fun insert(product: Product){
        repository.insert(product)
    }
    fun update(product: Product){
        repository.update(product)
    }
    fun delete(id: Int){
        repository.delete(id)
    }
    fun getAllData(): List<Product>{
        return products
    }
}