package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class AddDiscountToCartUseCase(val repository: IStoreRepository) {

    suspend operator fun invoke(discount: Discount): Response<Boolean> {
        val cartDiscounts = repository.getCartDiscounts()

        return if (cartDiscounts is Response.Success && cartDiscounts.data?.contains(discount) != true) {
            repository.addDiscountToCart(discount)
        } else {
            Response.Error("Discount already selected")
        }

    }

}