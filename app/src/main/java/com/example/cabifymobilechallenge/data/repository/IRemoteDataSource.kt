package com.example.cabifymobilechallenge.data.repository

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product

interface IRemoteDataSource {
    suspend fun getProducts(): Response<List<Product>>
    suspend fun makePayment(): Response<Unit>
    suspend fun getAvailableDiscounts(): Response<List<Discount>>
}