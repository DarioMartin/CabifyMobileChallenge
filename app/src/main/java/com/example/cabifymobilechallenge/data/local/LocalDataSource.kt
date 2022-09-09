package com.example.cabifymobilechallenge.data.local

import com.example.cabifymobilechallenge.domain.model.ShoppingCart

interface LocalDataSource {
    suspend fun saveShoppingCart(shoppingCart: ShoppingCart)
    suspend fun getSavedShoppingCart(): ShoppingCart
}