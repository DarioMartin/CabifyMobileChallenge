package com.example.cabifymobilechallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cabifymobilechallenge.data.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    suspend fun getAllProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query(
        "DELETE FROM product_table WHERE id IN " +
                "(SELECT id FROM product_table " +
                "WHERE type = :productType" +
                " LIMIT :limit)"
    )
    suspend fun delete(productType: String, limit: Int = 1)

    @Query("SELECT COUNT(*) FROM product_table WHERE type = :type")
    suspend fun countProductsByType(type: String): Int

    @Query("DELETE FROM product_table")
    suspend fun clearAll()
}