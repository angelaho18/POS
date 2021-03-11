package com.example.pointofsale

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var Nama: String = "NoName", var Email: String = "${if(Nama == "") "" else "$Nama@gmail.com" }") : Parcelable {
}