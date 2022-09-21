package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class GetProductsUseCase(private val repository: IStoreRepository) {

    suspend operator fun invoke(): Response<List<Product>> {

        val response = repository.getAvailableProducts()
        if (response is Response.Error) return response

        val remoteProducts: List<Product> = response.data ?: emptyList()
        remoteProducts.map {
            it.count = repository.getProductCount(it).data ?: 0
        }

        return Response.Success(remoteProducts)
    }

}