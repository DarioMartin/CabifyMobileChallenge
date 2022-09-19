package com.example.cabifymobilechallenge.domain.repository

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart

interface IStoreRepository {

    suspend fun getAvailableProducts(): Response<List<Product>>
    suspend fun getCartProducts(): Response<List<Product>>
    suspend fun addProductToCart(product: Product): Response<Unit>
    suspend fun removeProductFromCart(product: Product): Response<Unit>

    suspend fun getAvailableDiscounts(): Response<List<Discount>>
    suspend fun getCartDiscounts(): Response<List<Discount>>
    suspend fun addDiscountToCart(discount: Discount): Response<Boolean>
    suspend fun removeDiscountFromCart(discount: Discount): Response<Boolean>

    suspend fun makePayment(shoppingCart: ShoppingCart): Response<Boolean>

}