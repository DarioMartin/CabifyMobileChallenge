package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository
import kotlinx.coroutines.flow.Flow

class GetAvailableDiscountsUseCase(val repository: IStoreRepository) {

    suspend operator fun invoke(): Response<List<Discount>> {
        val discounts = repository.getAvailableDiscounts()

        if (discounts is Response.Success) {
            repository.addDiscountsToCart(discounts.data ?: emptyList())
        }

        return repository.getCartDiscounts()
    }

}