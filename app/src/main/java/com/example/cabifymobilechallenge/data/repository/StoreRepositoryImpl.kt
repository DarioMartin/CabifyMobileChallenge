package com.example.cabifymobilechallenge.data.repository

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import com.example.cabifymobilechallenge.domain.model.ShoppingCart
import com.example.cabifymobilechallenge.domain.repository.IStoreRepository

class StoreRepositoryImpl(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource
) : IStoreRepository {

    override suspend fun getAvailableProducts(): Response<List<Product>> {
        return remoteDataSource.getProducts()
    }

    override suspend fun addProductToCart(product: Product): Response<Unit> {
        return localDataSource.addProductToCart(product)
    }

    override suspend fun removeProductFromCart(product: Product): Response<Unit> {
        return localDataSource.removeProductFromCart(product)
    }

    override suspend fun getCartProducts(): Response<List<Product>> {
        return localDataSource.getCartProducts()
    }

    override suspend fun getAvailableDiscounts(): Response<List<Discount>> {
        return remoteDataSource.getAvailableDiscounts()
    }

    override suspend fun getCartDiscounts(): Response<List<Discount>> {
        return localDataSource.getCartDiscounts()
    }

    override suspend fun updateDiscount(discount: Discount): Response<Unit> {
        return localDataSource.updateDiscount(discount)
    }

    override suspend fun getProductCount(product: Product): Response<Int> {
        return localDataSource.getProductCount(product)
    }

    override suspend fun addDiscountsToCart(discounts: List<Discount>): Response<Unit> {
        return localDataSource.addDiscounts(discounts)
    }

    override suspend fun clearProducts(): Response<Unit> {
        return localDataSource.clearProducts()
    }

    override suspend fun clearDiscounts(): Response<Unit> {
        return localDataSource.clearDiscounts()
    }

    override suspend fun makeOrder(shoppingCart: ShoppingCart): Response<Unit> {
        return remoteDataSource.makeOrder(shoppingCart)
    }

}