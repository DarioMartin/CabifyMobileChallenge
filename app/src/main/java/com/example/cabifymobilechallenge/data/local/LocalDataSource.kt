package com.example.cabifymobilechallenge.data.local

import com.example.cabifymobilechallenge.data.Response
import com.example.cabifymobilechallenge.data.local.dao.DiscountDao
import com.example.cabifymobilechallenge.data.local.dao.ProductDao
import com.example.cabifymobilechallenge.data.repository.ILocalDataSource
import com.example.cabifymobilechallenge.domain.model.Discount
import com.example.cabifymobilechallenge.domain.model.Product

class LocalDataSource(
    private val productDao: ProductDao,
    private val discountDao: DiscountDao
) : ILocalDataSource {

    override suspend fun getCartProducts(): Response<List<Product>> {
        return try {
            val products = productDao.getAllProducts().map { it.toDomain() }
            Response.Success(data = products)
        } catch (e: Exception) {
            Response.Error("Error trying to recover products from database")
        }
    }

    override suspend fun addProductToCart(product: Product): Response<Unit> {
        return try {
            productDao.insertAll(listOf(product.toEntity()))
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error("Error trying to add product to database")
        }
    }

    override suspend fun removeProductFromCart(product: Product): Response<Unit> {
        return try {
            productDao.delete(product.toEntity().type)
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error("Error trying to remove product from database")
        }
    }

    override suspend fun getProductCount(product: Product): Response<Int> {
        return try {
            val count = productDao.countProductsByType(product.toEntity().type)
            Response.Success(count)
        } catch (e: Exception) {
            Response.Error("Error trying to get product count")
        }
    }


    override suspend fun removeDiscount(discount: Discount): Response<Unit> {
        return try {
            discountDao.delete(discount.toEntity())
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error("Error trying to remove discount from database")
        }
    }

    override suspend fun addDiscounts(discounts: List<Discount>): Response<Unit> {
        return try {
            discountDao.insertAll(discounts.map { it.toEntity() })
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error("Error trying to add discount to database")
        }
    }

    override suspend fun getCartDiscounts(): Response<List<Discount>> {
        return try {
            val discounts = discountDao.getDiscounts().map { it.toDomain() }
            Response.Success(data = discounts)
        } catch (e: Exception) {
            Response.Error("Error trying to recover discounts from database")
        }
    }

    override suspend fun updateDiscount(discount: Discount): Response<Unit> {
        return try {
            discountDao.update(discount.toEntity())
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error("Error trying to update discount in database")
        }
    }

}