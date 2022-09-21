package com.example.cabifymobilechallenge.data.remote

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.data.repository.IRemoteDataSource
import com.example.cabifymobilechallenge.data.toDomainProduct
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import java.lang.Exception

class MockedServerDataSource() : IRemoteDataSource {

    override suspend fun getProducts(): Response<List<Product>> {
        return Response.Success(
            listOf(
                Product.Voucher("Voucher", 5.0),
                Product.TShirt("Tshirt", 20.0),
                Product.Mug("Mug", 7.5)
            )
        )
    }

    override suspend fun getAvailableDiscounts(): Response<List<Discount>> {
        return Response.Success(
            listOf(
                Discount.TwoPer1VoucherPromo,
                Discount.TShirtBulkPromo
            )
        )
    }

    override suspend fun makeOrder(shoppingCart: ShoppingCart): Response<Unit> {
        return Response.Success(Unit)
    }
}