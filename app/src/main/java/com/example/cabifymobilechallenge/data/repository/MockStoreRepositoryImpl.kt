package com.example.cabifymobilechallenge.data.repository

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class MockStoreRepositoryImpl() : IStoreRepository {

    override suspend fun getProducts(): Response<List<Product>> {
        return Response.Success(
            listOf(
                Product.Voucher("Vochure", 2.6),
                Product.TShirt("Tshirt", 3.6),
                Product.Mug("Mug", 5.6)
            )
        )
    }

    override suspend fun makePayment(shoppingCart: ShoppingCart): Response<Boolean> {
        return Response.Error("")
    }

}