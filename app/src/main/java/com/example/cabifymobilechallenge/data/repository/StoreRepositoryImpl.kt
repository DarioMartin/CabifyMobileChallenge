package com.example.cabifymobilechallenge.data.repository

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class StoreRepositoryImpl() : IStoreRepository {

    override suspend fun getProducts(): Response<List<Product>> {
        return Response.Error("")
    }

    override suspend fun makePayment(shoppingCart: ShoppingCart): Response<Boolean> {
        return Response.Error("")
    }

}