package com.example.cabifymobilechallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cabifymobilechallenge.data.local.dao.ProductDao
import com.example.cabifymobilechallenge.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class StoreDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

}
