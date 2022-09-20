package com.example.cabifymobilechallenge.domain.repository

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import kotlinx.coroutines.flow.Flow

interface IStoreRepository {

    suspend fun getAvailableProducts(): Response<List<Product>>
    suspend fun getCartProducts(): Response<List<Product>>
    suspend fun getProductCount(product: Product): Response<Int>
    suspend fun addProductToCart(product: Product): Response<Unit>
    suspend fun removeProductFromCart(product: Product): Response<Unit>

    suspend fun getAvailableDiscounts(): Response<List<Discount>>
    suspend fun getCartDiscounts(): Response<List<Discount>>
    suspend fun addDiscountToCart(discount: Discount): Response<Unit>
    suspend fun removeDiscountFromCart(discount: Discount): Response<Unit>

    suspend fun makePayment(shoppingCart: ShoppingCart): Response<Unit>

}