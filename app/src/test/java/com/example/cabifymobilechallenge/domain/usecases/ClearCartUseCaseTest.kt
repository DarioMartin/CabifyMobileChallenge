package com.example.cabifymobilechallenge.domain.usecases

import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.util.stream.Collectors.toList

@ExperimentalCoroutinesApi
internal class ClearCartUseCaseTest : UseCaseTest() {

    private lateinit var successUseCase: ClearCartUseCase
    private lateinit var errorUseCase: ClearCartUseCase

    @Before
    override fun setUp() {
        super.setUp()
        successUseCase = ClearCartUseCase(successRepository)
        errorUseCase = ClearCartUseCase(errorRepository)
    }

    @Test
    fun `Test clear cart success`() = runTest {
        successUseCase.invoke()
        Truth.assertThat(successLocalDataSource.getProductList()).isEmpty()
        Truth.assertThat(successLocalDataSource.getDiscountList()).isEmpty()
    }

    @Test
    fun `Test clear cart error`() = runTest {
        val products = errorLocalDataSource.getProductList()
        val discounts = errorLocalDataSource.getDiscountList()
        errorUseCase.invoke()
        Truth.assertThat(products).isEqualTo(errorLocalDataSource.getProductList())
        Truth.assertThat(discounts).isEqualTo(errorLocalDataSource.getDiscountList())
    }

}