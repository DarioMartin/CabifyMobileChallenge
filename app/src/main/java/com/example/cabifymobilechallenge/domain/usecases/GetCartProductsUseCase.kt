package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class GetCartProductsUseCase(private val repository: IStoreRepository) {

    suspend operator fun invoke(): Response<List<Product>> {
        return Response.Error("")//repository.getCartProducts()
    }

}