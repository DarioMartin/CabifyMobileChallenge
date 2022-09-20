package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository
import kotlinx.coroutines.flow.*

class GetProductsUseCase(private val repository: IStoreRepository) {

    suspend operator fun invoke(): Response<List<Product>> {
        val remoteProducts: List<Product> = repository.getAvailableProducts().data ?: emptyList()
        remoteProducts.map {
            it.count = repository.getProductCount(it).data ?: 0
        }
        return Response.Success(remoteProducts)
    }

}