package com.example.cabifymobilechallenge.domain.repository

import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart

interface IStoreRepository {
    suspend fun getProducts(): List<Product>
    suspend fun makePayment(shoppingCart: ShoppingCart)
}