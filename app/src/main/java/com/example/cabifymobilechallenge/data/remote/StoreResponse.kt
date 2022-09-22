package com.example.cabifymobilechallenge.data.remote

data class StocksResponse(val products: List<RemoteProduct>)

data class RemoteProduct(
    val code: String,
    val name: String,
    val price: Double
)