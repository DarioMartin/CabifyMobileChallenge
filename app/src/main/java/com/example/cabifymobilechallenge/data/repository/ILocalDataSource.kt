package com.example.cabifymobilechallenge.data.repository

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product

interface ILocalDataSource {
    suspend fun getCartProducts(): Response<List<Product>>
    suspend fun addProductToCart(product: Product): Response<Unit>
    suspend fun removeProductFromCart(product: Product): Response<Unit>
    suspend fun removeDiscountFromCart(discount: Discount): Response<Unit>
    suspend fun addDiscountToCart(discount: Discount): Response<Unit>
    suspend fun getCartDiscounts(): Response<List<Discount>>
}