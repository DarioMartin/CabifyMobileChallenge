package com.example.cabifymobilechallenge.domain.usecases

import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
internal class AddProductToCartUseCaseTest : UseCaseTest() {

    private lateinit var successUseCase: AddProductToCartUseCase
    private lateinit var errorUseCase: AddProductToCartUseCase

    @Before
    override fun setUp() {
        super.setUp()
        successUseCase = AddProductToCartUseCase(successRepository)
        errorUseCase = AddProductToCartUseCase(errorRepository)
    }

    @Test
    fun `Test add an item success`() = runTest {
        val products = successServerDataSource.products
        val product = products.first()
        val initialCount: Int = successLocalDataSource.getProductCount(product).data ?: 0
        successUseCase.invoke(product)
        val count = successLocalDataSource.getProductCount(product).data ?: 0
        Truth.assertThat(count).isEqualTo(initialCount + 1)
    }

    @Test
    fun `Test add an item error`() = runTest {
        val products = errorServerDataSource.products.toList()
        val product = products.first()
        errorUseCase.invoke(product)
        Truth.assertThat(products).isEqualTo(errorServerDataSource.products)
    }

}