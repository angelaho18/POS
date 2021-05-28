package com.example.pointofsale

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [Product::class], version = 1)
@TypeConverters(ImageBitmapString::class)
abstract class ProductDBHelper: RoomDatabase() {
    abstract fun productDao(): ProductDAO
}
