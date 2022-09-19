package com.example.cabifymobilechallenge.data.repository

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class StoreRepositoryImpl() : IStoreRepository {

    override suspend fun getAvailableProducts(): Response<List<Product>> {
        return Response.Error("")
    }

    override suspend fun addProductToCart(product: Product): Response<Unit> {
        return Response.Error("")
    }

    override suspend fun removeProductFromCart(product: Product): Response<Unit> {
        return Response.Error("")
    }

    override suspend fun makePayment(shoppingCart: ShoppingCart): Response<Boolean> {
        return Response.Error("")
    }

    override suspend fun getCartProducts(): Response<List<Product>> {
        return Response.Error("")
    }

    override suspend fun getAvailableDiscounts(): Response<List<Discount>> {
        return Response.Error("")
    }

    override suspend fun getCartDiscounts(): Response<List<Discount>> {
        return Response.Error("")
    }

    override suspend fun addDiscountToCart(discount: Discount): Response<Boolean> {
        return Response.Error("")
    }

    override suspend fun removeDiscountFromCart(discount: Discount): Response<Boolean> {
        return Response.Error("")
    }

}