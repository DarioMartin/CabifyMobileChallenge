package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class ProcessOrderUseCase(private val repository: IStoreRepository) {

    suspend operator fun invoke(): Response<Unit> {
        return try {

            val productsResponse = repository.getCartProducts()
            if (productsResponse is Response.Error) return Response.Error(productsResponse.message)

            val discountsResponse = repository.getCartDiscounts()
            if (discountsResponse is Response.Error) return Response.Error(discountsResponse.message)

            val shoppingCart = ShoppingCart(
                productsResponse.data ?: emptyList(),
                discountsResponse.data ?: emptyList()
            )

            val orderResponse = repository.makeOrder(shoppingCart)
            if (orderResponse is Response.Error) return Response.Error(orderResponse.message)

            val clearProductResponse = repository.clearProducts()
            if (clearProductResponse is Response.Error) return Response.Error(clearProductResponse.message)

            val clearDiscountsResponse = repository.clearDiscounts()
            if (clearDiscountsResponse is Response.Error) return Response.Error(
                clearDiscountsResponse.message
            )

            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error("We could not perform the action")
        }
    }

}