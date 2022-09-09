package com.example.cabifymobilechallenge.data.repository

import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class StoreRepositoryImpl() : IStoreRepository {

    override suspend fun getProducts(): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun makePayment(shoppingCart: ShoppingCart) {
        TODO("Not yet implemented")
    }

}