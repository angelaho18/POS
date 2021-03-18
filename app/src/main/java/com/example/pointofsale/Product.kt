package com.example.pointofsale

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(var ProductName: String, var ProductPic: String, var Quantity: Int = 0, var Price: Int = 0): Parcelable {
}