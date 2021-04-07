package com.example.pointofsale

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Product(var ProductName: String, var ProductPic: String, var Quantity: Int = 0, var Price: Int = 0): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ProductName)
        parcel.writeString(ProductPic)
        parcel.writeInt(Quantity)
        parcel.writeInt(Price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}