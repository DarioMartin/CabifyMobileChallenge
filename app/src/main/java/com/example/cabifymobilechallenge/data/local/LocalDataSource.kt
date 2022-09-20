package com.example.cabifymobilechallenge.data.local

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.data.local.dao.ProductDao
import com.example.cabifymobilechallenge.data.repository.ILocalDataSource
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSource(private val productDao: ProductDao) : ILocalDataSource {
    override suspend fun getCartProducts(): Response<List<Product>> {
        return try {
            val products = productDao.getAllProducts().map { it.toDomainProduct() }
            Response.Success(data = products)
        } catch (e: Exception) {
            Response.Error("")
        }
    }

    override suspend fun addProductToCart(product: Product): Response<Unit> {
        return try {
            productDao.insertAll(listOf(product.toProductEntity()))
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error("")
        }
    }

    override suspend fun removeProductFromCart(product: Product): Response<Unit> {
        return try {
            productDao.delete(product.toProductEntity().type)
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error("")
        }
    }

    override suspend fun removeDiscountFromCart(discount: Discount): Response<Unit> {
        return Response.Error("")
    }

    override suspend fun addDiscountToCart(discount: Discount): Response<Unit> {
        return Response.Error("")
    }

    override suspend fun getCartDiscounts(): Response<List<Discount>> {
        return Response.Error("")
    }

    override suspend fun getProductCount(product: Product): Response<Int> {
        return try {
            val count = productDao.countProductsByType(product.toProductEntity().type)
            Response.Success(count)
        } catch (e: Exception) {
            Response.Error("")
        }
    }

}