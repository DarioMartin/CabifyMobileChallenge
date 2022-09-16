package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class GetAvailableDiscountsUseCase(val repository: IStoreRepository) {

    suspend operator fun invoke(): Response<List<Discount>> {
        return repository.getAvailableDiscounts()
    }

}