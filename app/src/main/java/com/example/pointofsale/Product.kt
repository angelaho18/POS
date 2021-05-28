package com.example.pointofsale

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "PRODUCT_IMG") var ProductPic: String = "",
    @ColumnInfo(name = "PRODUCT_NAME") var ProductName: String = "",
    @ColumnInfo(name = "PRODUCT_QTY") var Quantity: Int = 0,
    @ColumnInfo(name = "PRODUCT_PRICE") var Price: Int = 0
)

//data class Product(var ProductName: String, var ProductPic: String, var Quantity: Int = 0, var Price: Int = 0): Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString().toString(),
//        parcel.readString().toString(),
//        parcel.readInt(),
//        parcel.readInt()
//    )
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(ProductName)
//        parcel.writeString(ProductPic)
//        parcel.writeInt(Quantity)
//        parcel.writeInt(Price)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Product> {
//        override fun createFromParcel(parcel: Parcel): Product {
//            return Product(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Product?> {
//            return arrayOfNulls(size)
//        }
//    }
//}