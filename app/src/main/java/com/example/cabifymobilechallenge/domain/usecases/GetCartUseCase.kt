package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class GetCartUseCase(private val repository: IStoreRepository) {

    suspend operator fun invoke(): Response<ShoppingCart> {

        val cartProducts: List<Product> =
            emptyList()//repository.getCartProducts().data ?: emptyList()
        val cartDiscounts: List<Discount> = repository.getCartDiscounts().data ?: emptyList()

        return Response.Success(ShoppingCart(products = cartProducts, discounts = cartDiscounts))
    }

}
