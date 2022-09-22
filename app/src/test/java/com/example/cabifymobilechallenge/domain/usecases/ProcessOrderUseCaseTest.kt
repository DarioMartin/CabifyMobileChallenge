package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ProcessOrderUseCaseTest : UseCaseTest() {

    private lateinit var successUseCase: ProcessOrderUseCase
    private lateinit var errorUseCase: ProcessOrderUseCase

    @Before
    override fun setUp() {
        super.setUp()
        successUseCase = ProcessOrderUseCase(successRepository)
        errorUseCase = ProcessOrderUseCase(errorRepository)
    }

    @Test
    fun `Test process order success`() = runTest {
        successUseCase.invoke()

        val products = successLocalDataSource.products
        val discounts = successLocalDataSource.discounts

        Truth.assertThat(products).isEmpty()
        Truth.assertThat(discounts).isEmpty()
    }

    @Test
    fun `Test process order error`() = runTest {
        errorUseCase.invoke()

        val products = errorLocalDataSource.products
        val discounts = errorLocalDataSource.discounts

        Truth.assertThat(products).isNotEmpty()
        Truth.assertThat(discounts).isNotEmpty()
    }

}