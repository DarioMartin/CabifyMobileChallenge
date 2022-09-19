package com.example.cabifymobilechallenge.data.repository

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class MockStoreRepositoryImpl() : IStoreRepository {

    private val cartProducts = mutableListOf<Product>()
    private val cartDiscounts = mutableListOf<Discount>()

    override suspend fun getAvailableProducts(): Response<List<Product>> {
        return Response.Success(
            listOf(
                Product.Voucher("Vochure", 5.0),
                Product.TShirt("Tshirt", 20.0),
                Product.Mug("Mug", 7.5)
            )
        )
    }

    override suspend fun addProductToCart(product: Product): Response<Unit> {
        cartProducts.add(product)
        return Response.Success(Unit)
    }

    override suspend fun removeProductFromCart(product: Product): Response<Unit> {
        cartProducts.remove(product)
        return Response.Success(Unit)
    }

    override suspend fun makePayment(shoppingCart: ShoppingCart): Response<Boolean> {
        return Response.Error("")
    }

    override suspend fun getCartProducts(): Response<List<Product>> {
        return Response.Success(cartProducts)
    }

    override suspend fun getAvailableDiscounts(): Response<List<Discount>> {
        return Response.Success(
            listOf(
                Discount.TwoPer1VoucherPromo,
                Discount.TShirtBulkPromo
            )
        )
    }

    override suspend fun getCartDiscounts(): Response<List<Discount>> {
        return Response.Success(cartDiscounts)
    }

    override suspend fun addDiscountToCart(discount: Discount): Response<Boolean> {
        cartDiscounts.add(discount)
        return Response.Success(true)
    }

    override suspend fun removeDiscountFromCart(discount: Discount): Response<Boolean> {
        cartDiscounts.remove(discount)
        return Response.Success(true)
    }

}