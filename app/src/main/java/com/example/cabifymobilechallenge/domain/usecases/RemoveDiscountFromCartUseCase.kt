package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class RemoveDiscountFromCartUseCase(private val repository: IStoreRepository) {

    suspend operator fun invoke(discount: Discount): Response<Boolean> {
        return repository.removeDiscountFromCart(discount)
    }
}
