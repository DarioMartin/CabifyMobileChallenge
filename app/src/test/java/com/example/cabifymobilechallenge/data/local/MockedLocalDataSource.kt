package com.example.cabifymobilechallenge.data.local

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.data.repository.ILocalDataSource
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product

internal class MockedLocalDataSource(
    private val products: MutableList<Product> = mutableListOf(
        Product.Voucher("Voucher", 5.0),
        Product.TShirt("Tshirt", 20.0),
        Product.Mug("Mug", 7.5)
    ),
    private var discounts: MutableList<Discount> = mutableListOf(
        Discount.TwoPer1VoucherPromo,
        Discount.TShirtBulkPromo
    ),
    var success: Boolean = true
) :
    ILocalDataSource {
    override suspend fun getCartProducts(): Response<List<Product>> {
        return if (success) Response.Success(products.toList()) else Response.Error("Error")
    }

    override suspend fun addProductToCart(product: Product): Response<Unit> {
        return if (success) {
            products.add(product)
            Response.Success(Unit)
        } else Response.Error("Error")
    }

    override suspend fun removeProductFromCart(product: Product): Response<Unit> {
        return if (success) {
            val index = products.indexOfFirst { it.javaClass == product.javaClass }
            products.removeAt(index)
            Response.Success(Unit)
        } else Response.Error("Error")
    }

    override suspend fun removeDiscount(discount: Discount): Response<Unit> {
        return if (success) {
            discounts.remove(discount)
            Response.Success(Unit)
        } else Response.Error("Error")
    }

    override suspend fun addDiscounts(discounts: List<Discount>): Response<Unit> {
        return if (success) {
            this.discounts.addAll(discounts)
            Response.Success(Unit)
        } else Response.Error("Error")
    }

    override suspend fun updateDiscount(discount: Discount): Response<Unit> {
        return if (success) {
            discounts.indexOf(discount).let {
                if (it >= 0) discounts[it] = discount
            }
            Response.Success(Unit)
        } else Response.Error("Error")
    }

    override suspend fun getCartDiscounts(): Response<List<Discount>> {
        return if (success) Response.Success(discounts) else Response.Error("Error")
    }

    override suspend fun getProductCount(product: Product): Response<Int> {
        return if (success) {
            val count = products.count { it.javaClass == product.javaClass }
            Response.Success(count)
        } else Response.Error("Error")
    }

    override suspend fun clearDiscounts(): Response<Unit> {
        return if (success) {
            discounts.clear()
            Response.Success(Unit)
        } else Response.Error("Error")
    }

    override suspend fun clearProducts(): Response<Unit> {
        return if (success) {
            products.clear()
            Response.Success(Unit)
        } else Response.Error("Error")
    }

    fun getProductList(): List<Product> = products.toList()
    fun getDiscountList(): List<Discount> = discounts.toList()

}
