package com.example.cabifymobilechallenge.data.remote

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.data.local.dao.ProductDao
import com.example.cabifymobilechallenge.data.repository.IRemoteDataSource
import com.example.cabifymobilechallenge.data.toDomainProduct
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import com.example.cabifymobilechallenge.presentation.compose.components.getProductResource
import java.lang.Exception

internal class MockedServerDataSource(
    val products: MutableList<Product> = mutableListOf(
        Product.Voucher("Voucher", 5.0),
        Product.TShirt("Tshirt", 20.0),
        Product.Mug("Mug", 7.5)
    ),
    var discounts: MutableList<Discount> = mutableListOf(
        Discount.TwoPer1VoucherPromo,
        Discount.TShirtBulkPromo
    ),
    var success: Boolean = true
) : IRemoteDataSource {

    override suspend fun getProducts(): Response<List<Product>> {
        return if (success) Response.Success(products) else Response.Error("Error")
    }

    override suspend fun getAvailableDiscounts(): Response<List<Discount>> {
        return if (success) Response.Success(discounts) else Response.Error("Error")
    }

    override suspend fun makeOrder(shoppingCart: ShoppingCart): Response<Unit> {
        return if (success) Response.Success(Unit) else Response.Error("Error")
    }
}