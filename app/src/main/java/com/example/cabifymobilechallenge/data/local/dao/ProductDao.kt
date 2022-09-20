package com.example.cabifymobilechallenge.data.local.dao

import androidx.room.*
import com.example.cabifymobilechallenge.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    fun getAll(): Flow<List<ProductEntity>>

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

}