package com.example.cabifymobilechallenge.data.repository

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {
    suspend fun getCartProducts(): Response<List<Product>>
    suspend fun addProductToCart(product: Product): Response<Unit>
    suspend fun removeProductFromCart(product: Product): Response<Unit>
    suspend fun removeDiscount(discount: Discount): Response<Unit>
    suspend fun addDiscounts(discounts: List<Discount>): Response<Unit>
    suspend fun updateDiscount(discount: Discount): Response<Unit>
    suspend fun getCartDiscounts(): Response<List<Discount>>
    suspend fun getProductCount(product: Product): Response<Int>
    suspend fun clearDiscounts(): Response<Unit>
    suspend fun clearProducts(): Response<Unit>

}