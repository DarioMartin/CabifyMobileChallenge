package com.example.cabifymobilechallenge.data.remote

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.data.repository.IRemoteDataSource
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart

internal class MockedServerDataSource(var success: Boolean = true) : IRemoteDataSource {

    override suspend fun getProducts(): Response<List<Product>> {
        return if (success) Response.Success(getProductList()) else Response.Error("Error")
    }

    override suspend fun getAvailableDiscounts(): Response<List<Discount>> {
        return if (success) Response.Success(getDiscountList()) else Response.Error("Error")
    }

    override suspend fun makeOrder(shoppingCart: ShoppingCart): Response<Unit> {
        return if (success) Response.Success(Unit) else Response.Error("Error")
    }

    fun getProductList(): List<Product> {
        return mutableListOf(
            Product.Voucher("Voucher", 5.0),
            Product.TShirt("Tshirt", 20.0),
            Product.Mug("Mug", 7.5)
        )
    }

    fun getDiscountList(): List<Discount> {
        return mutableListOf(
            Discount.TwoPer1VoucherPromo,
            Discount.TShirtBulkPromo
        )
    }
}