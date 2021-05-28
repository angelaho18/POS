package com.example.pointofsale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDAO {
    @Query("Select * from Product order by PRODUCT_NAME asc")
    fun getAllData(): List<Product>

    @Insert
    fun insertAll(vararg product: Product)

    @Query("Update Product set PRODUCT_QTY=:qty, PRODUCT_PRICE=:price, PRODUCT_IMG=:pic where PRODUCT_NAME=:name")
    fun updateData(name: String, pic: String, qty: Int, price: Int)

    @Query("Delete from Product where PRODUCT_NAME=:name")
    fun deleteData(name: String)
}