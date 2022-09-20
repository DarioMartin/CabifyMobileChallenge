package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class ProcessOrderUseCase(private val repository: IStoreRepository) {

    suspend operator fun invoke(): Response<Unit> {
        return try {
            val productsResponse = repository.clearProducts()
            val discountsResponse = repository.clearDiscounts()

            if (productsResponse is Response.Success && discountsResponse is Response.Success) {
                Response.Success(Unit)
            } else {
                Response.Error("We could not perform the action")
            }
        } catch (e: Exception) {
            Response.Error("We could not perform the action")
        }
    }

}