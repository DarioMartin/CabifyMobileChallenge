package com.example.cabifymobilechallenge.data.local

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.data.repository.ILocalDataSource
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class MockedLocalDataSource : ILocalDataSource {
    override suspend fun getCartProducts(): Response<List<Product>> {
        return Response.Success(emptyList())
    }

    override suspend fun addProductToCart(product: Product): Response<Unit> {
        return Response.Success(Unit)
    }

    override suspend fun removeProductFromCart(product: Product): Response<Unit> {
        return Response.Success(Unit)
    }

    override suspend fun removeDiscountFromCart(discount: Discount): Response<Unit> {
        return Response.Success(Unit)
    }

    override suspend fun addDiscountToCart(discount: Discount): Response<Unit> {
        return Response.Success(Unit)
    }

    override suspend fun getCartDiscounts(): Response<List<Discount>> {
        return Response.Success(emptyList())
    }

    override suspend fun getProductCount(product: Product): Response<Int> {
        return Response.Success(0)
    }

}