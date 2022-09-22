package com.example.cabifymobilechallenge.domain.usecases

import com.example.cabifymobilechallenge.data.Response
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
internal class GetCartProductsUseCaseTest : UseCaseTest(){

    private lateinit var successUseCase: GetCartProductsUseCase
    private lateinit var errorUseCase: GetCartProductsUseCase

    @Before
    override fun setUp() {
        super.setUp()
        successUseCase = GetCartProductsUseCase(successRepository)
        errorUseCase = GetCartProductsUseCase(errorRepository)
    }

    @Test
    fun `Test get cart products success`() = runTest {
        val products = successLocalDataSource.products.toList()
        val response = successUseCase.invoke()
        Truth.assertThat(response.data).isEqualTo(products)
    }

    @Test
    fun `Test get cart products error`() = runTest {
        val response = errorUseCase.invoke()
        Truth.assertThat(response).isInstanceOf(Response.Error::class.java)
    }

}