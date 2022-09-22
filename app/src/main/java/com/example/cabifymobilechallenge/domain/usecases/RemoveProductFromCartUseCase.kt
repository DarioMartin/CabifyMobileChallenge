package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class RemoveProductFromCartUseCase(private val repository: IStoreRepository) {

    suspend operator fun invoke(product: Product): Response<Unit> {
        return repository.removeProductFromCart(product)
    }

}