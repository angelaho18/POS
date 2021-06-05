package com.example.pointofsale.Room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pointofsale.Room.Product

@Dao
interface ProductDAO {
    @Query("Select count(*) from Product")
    fun count(): Int

    @Query("Select * from Product order by PRODUCT_NAME asc")
    fun getAllData(): LiveData<List<Product>>

    @Insert
    fun insertAll(vararg product: Product)

    @Update
    fun updateData(product: Product)

    @Delete
    fun deleteData(product: Product)

    @Query("Delete from Product where id=:id")
    fun deleteById(id: Int)

}
//abstract class ProductDAO {
////    @Transaction
////    abstract fun insertUser(product: Product)
//
//    @Query("Select * from Product order by PRODUCT_NAME asc")
//    abstract fun getAllData(): List<Product>
//
//    @Query("Delete from Product where PRODUCT_NAME=:name")
//    abstract fun deleteByName(name: String)
//
//    @Insert
//    abstract fun insertAll(vararg product: Product)
//
//    @Update
//    abstract fun updateData(product: Product)
//}