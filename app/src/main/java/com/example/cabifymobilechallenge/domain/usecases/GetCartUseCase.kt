package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class GetCartUseCase(private val repository: IStoreRepository) {

    suspend operator fun invoke(): Response<ShoppingCart> {

        val cartProducts = repository.getCartProducts().data ?: emptyList()
        val cartDiscounts = repository.getCartDiscounts().data ?: emptyList()

        return Response.Success(ShoppingCart(products = cartProducts, discounts = cartDiscounts))
    }

}
