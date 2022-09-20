package com.example.cabifymobilechallenge.data.local

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.data.repository.ILocalDataSource
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product

class LocalDataSource : ILocalDataSource {
    override suspend fun getCartProducts(): Response<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun addProductToCart(product: Product): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun removeProductFromCart(product: Product): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun removeDiscountFromCart(discount: Discount): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun addDiscountToCart(discount: Discount): Response<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getCartDiscounts(): Response<List<Discount>> {
        TODO("Not yet implemented")
    }

}