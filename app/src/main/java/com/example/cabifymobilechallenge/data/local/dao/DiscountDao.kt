package com.example.cabifymobilechallenge.data.local.dao

import androidx.room.*
import com.example.cabifymobilechallenge.data.local.entity.DiscountEntity

@Dao
interface DiscountDao {

    @Query("SELECT * FROM discount_table")
    suspend fun getDiscounts(): List<DiscountEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(discounts: List<DiscountEntity>)

    @Delete
    suspend fun delete(discount: DiscountEntity)

    @Update
    suspend fun update(discount: DiscountEntity)

    @Query("DELETE FROM discount_table")
    suspend fun clearAll()

}