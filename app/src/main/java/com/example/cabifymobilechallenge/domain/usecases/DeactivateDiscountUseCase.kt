package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class DeactivateDiscountUseCase(private val repository: IStoreRepository) {

    suspend operator fun invoke(discount: Discount): Response<Unit> {
        return repository.updateDiscount(discount.apply { active = false })
    }
}
