package com.example.cabifymobilechallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cabifymobilechallenge.data.local.dao.DiscountDao
import com.example.cabifymobilechallenge.data.local.dao.ProductDao
import com.example.cabifymobilechallenge.data.local.entity.DiscountEntity
import com.example.cabifymobilechallenge.data.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class, DiscountEntity::class],
    version = 1
)

abstract class StoreDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

    abstract fun getDiscountDao(): DiscountDao

}
