package com.example.cabifymobilechallenge.data.remote

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.data.repository.IRemoteDataSource
import com.example.cabifymobilechallenge.data.toDomainProduct
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import java.lang.Exception

class ServerDataSource(private val storeApi: StoreApi) : IRemoteDataSource {

    override suspend fun getProducts(): Response<List<Product>> {
        return try {
            val serverProducts = storeApi.getStocks()
            val products =
                serverProducts.body()?.products?.map { it.toDomainProduct() } ?: emptyList()
            Response.Success(data = products)
        } catch (e: Exception) {
            Response.Error(e.message ?: "Could not load the stocks")
        }
    }

    override suspend fun getAvailableDiscounts(): Response<List<Discount>> {
        //mocked data
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