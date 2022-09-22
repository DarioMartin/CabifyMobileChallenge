package com.example.cabifymobilechallenge.domain.usecases

import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class RemoveProductFromCartUseCaseTest : UseCaseTest() {

    private lateinit var successUseCase: RemoveProductFromCartUseCase
    private lateinit var errorUseCase: RemoveProductFromCartUseCase

    @Before
    override fun setUp() {
        super.setUp()
        successUseCase = RemoveProductFromCartUseCase(successRepository)
        errorUseCase = RemoveProductFromCartUseCase(errorRepository)
    }

    @Test
    fun `Test remove an item success`() = runTest {
        val products = successServerDataSource.getProductList()
        val product = products.first()
        val initialCount: Int = successLocalDataSource.getProductCount(product).data ?: 0
        successUseCase.invoke(product)
        val count = successLocalDataSource.getProductCount(product).data ?: 0
        Truth.assertThat(count).isEqualTo(initialCount - 1)
    }

    @Test
    fun `Test remove an item error`() = runTest {
        val products = errorServerDataSource.getProductList().toList()
        val product = products.first()
        errorUseCase.invoke(product)
        Truth.assertThat(products.size).isEqualTo(errorServerDataSource.getProductList().size)
    }

}