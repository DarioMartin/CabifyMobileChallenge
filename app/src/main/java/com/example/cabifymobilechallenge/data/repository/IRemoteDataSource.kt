package com.example.cabifymobilechallenge.data.repository

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart

interface IRemoteDataSource {
    suspend fun getProducts(): Response<List<Product>>
    suspend fun getAvailableDiscounts(): Response<List<Discount>>
    suspend fun makeOrder(shoppingCart: ShoppingCart): Response<Unit>
}